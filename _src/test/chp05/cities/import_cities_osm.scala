import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import scala.collection.immutable.List
import scala.io.Source
import scala.xml.Elem
import scala.xml.XML

// ARGS: import_cities_osm cities paintings
val cities_core = args { 0 }
val core = args { 1 }

// --------------- FUNCTIONS ---------------

// a method to post document to Solr using the XML format
def send_post(solr_core: String, xml_content: Elem): String = {
  val content = xml_content.toString
  val urlConn: HttpURLConnection = new URL(s"http://localhost:8983/solr/$solr_core/update?commit=true&wt=json").openConnection.asInstanceOf[HttpURLConnection]
  urlConn.setDoInput(true)
  urlConn.setDoOutput(true)
  urlConn.setUseCaches(false)
  urlConn.setRequestMethod("POST")
  urlConn.setRequestProperty("Content-Type", "application/xml")
  val out = new DataOutputStream(urlConn.getOutputStream)
  out.write(content.getBytes())
  out.flush
  out.close
  val in = urlConn.getInputStream()
  val result = io.Source.fromInputStream(in).mkString("")
  in.close
  result
}

// format timestamp according to Solr date format
def formatTimestamp(timestamp: String) = {
  val in = new SimpleDateFormat("EEE, d MMM yy HH:mm:ss Z", Locale.ENGLISH)
  val date = in.parse(timestamp)
  val out = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.ENGLISH)
  out.format(date)
}

// a method to normalize the name for the city
def normalizeName(name: String) = name.replace("\"", "").replace("http://dbpedia.org/resource/", "").replace("_", "%20").trim

// construct a list of documents to send to Solr (one for each one of the proposed result for each city)
def xml_add_docs(city: String, city_xml: Elem) = {
  val timestamp = formatTimestamp((city_xml \ "@timestamp").toString())
  val xml_docs = for (place <- city_xml \ "place") yield {
    <doc>
      <field name="id">{ place \ "@place_id" }</field>
      <field name="city">{ city }</field>
      <field name="display_name">{ place \ "@display_name" }</field>
      <field name="lat">{ place \ "@lat" }</field>
      <field name="lon">{ place \ "@lon" }</field>
      <field name="coordinates">{ place \ "@lat" },{ place \ "@lon" }</field>
      <field name="boundingbox">{ place \ "@boundingbox" }</field>
      <field name="importance">{ place \ "@importance" }</field>
      <field name="timestamp">{ timestamp }</field>
    </doc>
  }
  <add>{ xml_docs.toList }</add>
}

// ---- MAIN ----

// deletes all the documents from the index
def xml_clean = <delete><query>*:*</query></delete>

// start with a clean index
send_post(cities_core, xml_clean)
println("## index clear")

// obtain a plain list of all the city cited
val url = s"http://localhost:8983/solr/$core/select?q=(city:*)&fl=city&start=0&rows=10000&wt=csv&csv.header=false"

// for every city we found, we add the geolocalization information to the index
val cities = for (line <- Source.fromURL(url, "UTF-8").getLines) yield normalizeName(line)

cities.toSet[String].foreach(city => {

  println(s"CITY: $city")
  // use the nominatim service from Open StreetMap to obtain geolocalization details:
  val osm_query = s"http://nominatim.openstreetmap.org/search?city=$city&dedupe=1&format=xml"

  // generate the xml data to send to Solr
  val city_post = xml_add_docs(city, XML.load(osm_query))
  println("\t indexing city: " + city)
  val response = send_post(cities_core, city_post)
  println("\t response from Solr: " + response)
})