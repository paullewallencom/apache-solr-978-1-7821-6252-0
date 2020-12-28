import scala.xml.XML
import java.net.URL
import java.io.PrintStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.io.File
import java.io.FileOutputStream
import scala.io.Source
import java.util.Arrays
import java.text.PatternEntry
import java.util.regex.Pattern

def time = Calendar.getInstance().getTime().getTime()

def timeFormattedString(t: Long) = (new SimpleDateFormat("hh:mm:sss")).format(t)

def encodeHtmlEntities(url: String): String =
  url.trim()
    .replaceAll("\\{", "%7B")
    .replaceAll("\\}", "%7D")
    .replaceAll("\\(", "%28")
    .replaceAll("\\)", "%29")
    .replaceAll("<", "%3C")
    .replaceAll(">", "%3E")
    .replaceAll("\\?", "%3F")
    .replaceAll("\\/", "%2F")
    .replaceAll("\"", "%22")
    .replaceAll("'", "%27")
    .replaceAll(",", "%2C")
    .replaceAll(":", "%3A")
    .replaceAll("=", "%3D")
    .replaceAll("&", "%26")
    .replaceAll("\r", "%0D")
    .replaceAll("\n", "%0A")
    .replaceAll("\\s+", "+")

def decodeHtmlEntities(html: String) = html.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&eq;", "==").replaceAll("&quot;", "\"")

def normalizeXMLtext(xml: String): String = decodeHtmlEntities(xml).replaceAll("\\b(.+?)\\b=\"<.+?>(.+?)<.+?>\"", "$1=\"$2\"")

def downloadURL(url: URL, dirName: String) = {
  try {
    val name = url.getPath().toString().replaceAll("^.*/(.*?)$", "$1")
    val xml = XML.load(url).toString
    val text = normalizeXMLtext(xml)

    val dir = new File(dirName)
    if (!dir.exists()) dir.mkdirs()
    val outFile = new File(dir, name + ".xml")

    println("DOWNLOADING PAGE FROM: " + url)
    println("\tSAVE PAGE TO: " + outFile.getCanonicalPath() + "\n")

    (new PrintStream(new FileOutputStream(outFile))).println(text)
  } catch {
    case e: Throwable => System.err.println("ERROR with url: " + url + "\n" + e.getMessage())
  }
}

def sparqlQuery(endpoint: String): URL = {
  def url(q: String) = endpoint + "?query=" + q + "&format=text/csv&timeout=1000"
  val query = encodeHtmlEntities("""
		SELECT DISTINCT iri(concat(replace(str(?uri),"http://dbpedia.org/resource/","http://dbpedia.org/data/","i"), ".rdf")) AS ?painting
		WHERE { 
			?uri a ?type
			{?type rdf:type <http://dbpedia.org/class/yago/Painting103876519>}
			UNION
			{?type rdfs:subClassOf <http://dbpedia.org/class/yago/Painting103876519>}
		}
    	LIMIT 100000
	""")
  new URL(url(query))

}

def saveFile(dir: String, name: String, content: String) = {
  val file = new File(dir, name)
  if (!file.getParentFile().exists()) file.getParentFile().mkdirs()
  (new FileWriter(file)).write(content.toCharArray())
}

// ---- MAIN ----
val start = time

// reading arguments from commandline:
// EXAMPLE: downloadFromDBPedia http://dbpedia.org/sparql ../download
val endpoint = args { 0 }
val download_dir = args { 1 }

println("ENDPOINT: " + endpoint)
println("DOWNLOAD DIRECTORY: " + download_dir)

// getting a list of paintings, as CSV results, from SPARQL endpoint
val query = sparqlQuery(endpoint)
println("executing SPARQL QUERY:\n" + query)

// construct an array of paintings url, skipping first line and the wrapping " characters
val csv_list = Source.fromURL(query)("UTF-8")
val urls = (for (url <- csv_list.getLines.drop(1)) yield url.replaceAll("^\"(.*)\"$", "$1")).toArray

// download every RDF/XML file, and save it with xml extension 
for (i <- 0 to urls.size - 1) yield {
  printf("download [%d/%d] %s\n", i, urls.size, urls { i })
  downloadURL(new URL(urls(i)), download_dir)
}

val end = (time - start) / 1000.0
printf("download of paintings data completed (TIME: %s seconds)\n", end)
