<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:dbpedia-owl="http://dbpedia.org/ontology/" xmlns:foaf="http://xmlns.com/foaf/0.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:wdrs="http://www.w3.org/2007/05/powder-s#" xmlns:dbpprop="http://dbpedia.org/property/" xmlns:ns8="http://www.w3.org/ns/prov#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#" xmlns:grs="http://www.georss.org/georss/" exclude-result-prefixes="xsl rdf rdfs owl dbpedia-owl foaf dcterms wdrs dbpprop ns8">

	<xsl:output omit-xml-declaration="yes" indent="yes" />
	<xsl:param name="entity_type" />

	<xsl:template match="*|@*|text()">
		<xsl:apply-templates>
			<xsl:with-param name="entity_type" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template name="root" match="/">
		<add xmlns:dc="http://purl.org/dc/elements/1.1/" overwrite="true">
			<xsl:apply-templates>
				<xsl:with-param name="entity_type" />
			</xsl:apply-templates>
		</add>
	</xsl:template>


	<xsl:variable name="xpath_selectors">
		<xpath match="/owl:sameAs" name="sameAs" select="@rdf:resource">vediamo</xpath>
		<xpath match="/rdfs:label" name="sameAs" select="@rdf:resource">vediamo</xpath>
	</xsl:variable>

	<xsl:variable name="PROVA">
		<OK>VEDIAMO</OK>
		<OK>POI</OK>
	</xsl:variable>

	<xsl:template name="description" match="/rdf:RDF/rdf:Description">
		<doc>
			<field name="uri">
				<xsl:value-of select="@rdf:about" />
			</field>
			<!-- <field name="entity_type"><xsl:value-of select="$entity_type" /></field> -->
			<!-- <field name="timestamp">NOW</field>TIMESTAMP -->
			<xsl:apply-templates />

			<field name="sameAs"><xsl:value-of select="./owl:sameAs/@rdf:resource" /></field>
			<field name="primaryTopic"><xsl:value-of select="./foaf:primaryTopic/@rdf:resource" /></field>
			<field name="isPrimaryTopicOf"><xsl:value-of select="./foaf:isPrimaryTopicOf/@rdf:resource" /></field>
			<field name="label"><xsl:value-of select="./rdfs:label[@xml:lang='en']/text()" /></field>
			<field name="comment"><xsl:value-of select="./rdfs:comment[@xml:lang='en']/text()" /></field>
			<field name="abstract"><xsl:value-of select="./dbpedia-owl:abstract[@xml:lang='en']/text()" /></field>
			<field name="type"><xsl:value-of select="./rdf:type/text()" /></field>
			<field name="type"><xsl:value-of select="./dbpprop:type[@xml:lang='en']/text()" /></field>
			<field name="year"><xsl:value-of select="./dbpprop:year[@xml:lang='en']/text()" /></field>
			<field name="title"><xsl:value-of select="./dbpprop:title[@xml:lang='en']/text()" /></field>
			<field name="artist"><xsl:value-of select="./dbpprop:artist/@rdf:resource" /></field>
			<field name="museum"><xsl:value-of select="./dbpprop:museum/@rdf:resource" /></field>
			<field name="height"><xsl:value-of select="./dbpprop:height/text()" /></field>
			<field name="width"><xsl:value-of select="./dbpprop:width/text()" /></field>
			<field name="imageSize"><xsl:value-of select="./dbpprop:imageSize/text()" /></field>
			<field name="subject" update="add"><xsl:value-of select="./dcterms:subject/@rdf:resource" /></field>
			<field name="city"><xsl:value-of select="./dbpprop:city/@rdf:resource" /></field>
			<field name="thumbnail"><xsl:value-of select="./dbpedia-owl:thumbnail/@rdf:resource" /></field>
			<field name="image"><xsl:value-of select="./dbpprop:imageFile[@xml:lang='en']/@rdf:resource" /></field>
			<field name="photo-collection"><xsl:value-of select="./dbpprop:hasPhotoCollection/@rdf:resource" /></field>
			<field name="link_external"><xsl:value-of select="./dbpedia-owl:wikiPageExternalLink/@rdf:resource" /></field>
			<!-- Artist specific section -->
			<field name="field"><xsl:value-of select="./dbpprop:field/@rdf:resource | /dbpedia-owl:field/@rdf:resource" /></field>
			<field name="movement"><xsl:value-of select="./dbpprop:movement/@rdf:resource | /dbpedia-owl:movement/@rdf:resource" /></field>
			<field name="depiction"><xsl:value-of select="./foaf:depiction/@rdf:resource" /></field>
			<field name="name"><xsl:value-of select="./foaf:name[@xml:lang='en']/@rdf:resource" /></field>
			<field name="givenName"><xsl:value-of select="./foaf:givenName[@xml:lang='en']/@rdf:resource" /></field>
			<field name="surname"><xsl:value-of select="./foaf:surname[@xml:lang='en']/@rdf:resource" /></field>
			<field name="influencedBy"><xsl:value-of select="./dbpprop:influencedBy/@rdf:resource | ./dbpedia-owl:influencedBy/@rdf:resource" /></field>
			<field name="influenced"><xsl:value-of select="./dbpprop:influenced/@rdf:resource | ./dbpedia-owl:influenced/@rdf:resource | ./dbpprop:works/@rdf:resource" /></field>
			<field name="description"><xsl:value-of select="./dc:description/@rdf:resource" /></field>
			<field name="caption"><xsl:value-of select="./dbpprop:captions/@rdf:resource" /></field>
			<field name="coverArtist"><xsl:value-of select="./dbpedia-owl:coverArtist/@rdf:resource" /></field>
			<field name="coverArtist"><xsl:value-of select="./dbpprop:coverArtist/@rdf:resource" /></field>
			<field name="birth-name"><xsl:value-of select="./dbpedia-owl:birthName/@rdf:resource" /></field>
			<field name="birth-place"><xsl:value-of select="./dbpedia-owl:birthPlace/@rdf:resource | /dbpedia-owl:placeOfBirth/@rdf:resource" /></field>
			<field name="death-place"><xsl:value-of select="./dbpedia-owl:deathPlace/@rdf:resource | /dbpedia-owl:placeOfDeath/@rdf:resource" /></field>
			<field name="birthDate"><xsl:value-of select="./dbpedia-owl:birthDate/@rdf:resource" /></field>
			<field name="deathhDate"><xsl:value-of select="./dbpedia-owl:deathhDate/@rdf:resource" /></field>
			<field name="movement"><xsl:value-of select="./dbpedia-owl:movement/@rdf:resource" /></field>
			<!-- museum -->
			<field name="location"><xsl:value-of select="./dbpprop:location/text()" /></field>
			<field name="location"><xsl:value-of select="./dbpprop:latitude/text() | /geo:lat/text()" /></field>
			<field name="location"><xsl:value-of select="./dbpprop:longitude/text() | /geo:long/text()" /></field>
			<field name="point"><xsl:value-of select="./grs:point[@xml:lang='en']/text()" /></field>
			<field name="point"><xsl:value-of select="./dbpprop:name[@xml:lang='en']/text()" /></field>
			<field name="established"><xsl:value-of select="./dbpprop:established/text()" /></field>
			<field name="visitors"><xsl:value-of select="./dbpprop:visitors/text()" /></field>
			<field name="homepage"><xsl:value-of select="./foaf:homepage/@rdf:resource" /></field>
			

		</doc>


	</xsl:template>


</xsl:stylesheet>
