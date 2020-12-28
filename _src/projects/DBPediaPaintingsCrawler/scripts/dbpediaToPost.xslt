<?xml version="1.0" encoding="UTF-8"?>
<!--
  Copyright 2013 Alfredo Serafini (http://seralf.it)
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
    http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:owl="http://www.w3.org/2002/07/owl#"
	xmlns:dbpedia-owl="http://dbpedia.org/ontology/" xmlns:foaf="http://xmlns.com/foaf/0.1/"
	xmlns:dcterms="http://purl.org/dc/terms/" xmlns:wdrs="http://www.w3.org/2007/05/powder-s#"
	xmlns:dbpprop="http://dbpedia.org/property/" xmlns:ns8="http://www.w3.org/ns/prov#"
	xmlns:dc="http://purl.org/dc/elements/1.1/" exclude-result-prefixes="xsl rdf rdfs owl dbpedia-owl foaf dcterms wdrs dbpprop ns8">

	<xsl:output omit-xml-declaration="yes" indent="yes" />

	<xsl:template match="*|@*|text()">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="/">
		<add xmlns:dc="http://purl.org/dc/elements/1.1/" overwrite="true">
			<xsl:apply-templates />
		</add>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description">
		<doc>
			<field name="uri">
				<xsl:value-of select="@rdf:about" />
			</field>
			<field name="timestamp">NOW</field><!-- TIMESTAMP -->
			<xsl:apply-templates />
		</doc>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/owl:sameAs">
		<field name="sameAs">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/foaf:primaryTopic">
		<field name="primaryTopic">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/foaf:isPrimaryTopicOf">
		<field name="isPrimaryTopicOf">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/rdfs:label[@xml:lang='en']">
		<field name="label">
			<xsl:value-of select="." />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/rdfs:comment[@xml:lang='en']">
		<field name="comment">
			<xsl:value-of select="." />
		</field>
	</xsl:template>

	<xsl:template
		match="/rdf:RDF/rdf:Description/dbpedia-owl:abstract[@xml:lang='en']">
		<field name="abstract">
			<xsl:value-of select="." />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:type[@xml:lang='en']">
		<field name="type">
			<xsl:value-of select="." />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:year[@xml:lang='en']">
		<field name="year">
			<xsl:value-of select="." />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:title[@xml:lang='en']">
		<field name="title">
			<xsl:value-of select="." />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:artist">
		<field name="artist">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:museum">
		<field name="museum">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:height">
		<field name="height">
			<xsl:value-of select="text()" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:width">
		<field name="width">
			<xsl:value-of select="./text()" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:imageSize">
		<field name="imageSize">
			<xsl:value-of select="text()" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dcterms:subject">
		<field name="subject" update="add">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:city">
		<field name="city">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpedia-owl:thumbnail">
		<field name="thumbnail">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template
		match="/rdf:RDF/rdf:Description/dbpprop:imageFile[@xml:lang='en']">
		<field name="image">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:hasPhotoCollection">
		<field name="photo-collection">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template
		match="/rdf:RDF/rdf:Description/dbpedia-owl:wikiPageExternalLink">
		<field name="link_external">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<!-- Artist specific section -->
	<xsl:template
		match="/rdf:RDF/rdf:Description/dbpedia-owl:birthPlace | /rdf:RDF/rdf:Description/dbpedia-owl:placeOfBirth">
		<field name="birth-place">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>
	<xsl:template
		match="/rdf:RDF/rdf:Description/dbpedia-owl:deathPlace | /rdf:RDF/rdf:Description/dbpedia-owl:placeOfDeath">
		<field name="death-place">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template
		match="/rdf:RDF/rdf:Description/dbpprop:field | /rdf:RDF/rdf:Description/dbpedia-owl:field">
		<field name="field">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template
		match="/rdf:RDF/rdf:Description/dbpprop:movement | /rdf:RDF/rdf:Description/dbpedia-owl:movement">
		<field name="movement">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/foaf:depiction">
		<field name="depiction">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/foaf:name[@xml:lang='en']">
		<field name="name">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>
	<xsl:template match="/rdf:RDF/rdf:Description/dbpedia-owl:birthName">
		<field name="birth-name">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:influencedBy">
		<field name="influenced">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:works">
		<field name="influenced">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dc:description">
		<field name="description">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

	<xsl:template match="/rdf:RDF/rdf:Description/dbpprop:captions">
		<field name="caption">
			<xsl:value-of select="@rdf:resource" />
		</field>
	</xsl:template>

</xsl:stylesheet>
