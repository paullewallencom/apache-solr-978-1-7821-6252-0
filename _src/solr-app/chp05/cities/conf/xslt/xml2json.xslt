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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
	<xsl:output method="text" encoding="UTF-8" media-type="text/plain" />

	<xsl:template match="/">
		<xsl:text>var cities = {"name":"cities","type":"FeatureCollection","features":[</xsl:text>
		<xsl:apply-templates select="*" />
		<xsl:text>]}</xsl:text>
	</xsl:template>

	<xsl:template match="//response/result/doc">
		
<!-- 		<xsl:variable name="max" select="//response/result[@name='response']/@numFound" /> -->
	
<!-- 	AAAAA<xsl:value-of select="position()" />AAA -->
<!-- 	BBBBB<xsl:value-of select="last()" />BBB -->
	
<!-- 	OOOO<xsl:value-of select="string(.)" />OOOO -->
	
	<xsl:variable name="max" select="last()-1" />
		
		<xsl:text>{"type":"Feature",</xsl:text>
		<xsl:text>"geometry":{"type":"Point","coordinates":[</xsl:text>
		<xsl:value-of select="./*[@name='lon']" />
		<xsl:text>,</xsl:text>
		<xsl:value-of select="./*[@name='lat']" />
		<xsl:text>]},</xsl:text>
		<xsl:text>"properties":{</xsl:text>
		<xsl:text>"id":</xsl:text><xsl:value-of select="./*[@name='id']" /><xsl:text>,</xsl:text>
		<xsl:text>"city":"</xsl:text><xsl:value-of select="./*[@name='city']" /><xsl:text>",</xsl:text>
		<xsl:text>"display_name":"</xsl:text><xsl:value-of select="./*[@name='display_name']" /><xsl:text>",</xsl:text>
		<xsl:text>"importance":</xsl:text><xsl:value-of select="./*[@name='importance']" />
		<xsl:text>}</xsl:text>
		<xsl:text>}</xsl:text>
		<xsl:if test="position() &lt; $max"><xsl:text>,</xsl:text></xsl:if>
	</xsl:template>

	<xsl:template match="text()" />

</xsl:stylesheet>




<!-- 
{
			"geometry":{
				"type":"Point",
				"coordinates":[-2.58405459058675,56.5733598685772]
			},
			"properties":{
				LABEL : "TEST-01",
				"STREETNAME":"BRECHIN ROAD"
			}
		},
 -->









<!-- 
<response>
	<lst name="responseHeader">
		<int name="status">0</int>
		<int name="QTime">1</int>
	</lst>
	<result name="response" numFound="10" start="0">
		<doc>
			<long name="id">97592906</long>
			<str name="city">London</str>
			<str name="display_name">
				London, Greater London, England, United Kingdom, European Union
			</str>
			<str name="lat">51.5072759</str>
			<str name="lon">-0.1276597</str>
			<str name="boundingbox">
				51.2867584228516,51.6918754577637,-0.510375142097473,0.334015518426895
			</str>
			<str name="importance">0.9654895765402</str>
			<str name="timestamp">2013-06-30T02:45:12.00Z</str>
		</doc>
		
...		
		
 -->
