<?xml version='1.0' encoding='utf-8'?>
<!--
    Document   : wsdl2rdf.xsl.xsl
    Created on : 5 juli 2016, 19:53
    Author     : Miguel
    Description:
        Purpose of transformation follows.
-->


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"

>
    <xsl:output method="text" />

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="fsb">
        <xsl:variable name="dcat" select="'http://www.w3.org/ns/dcat#'" />
        <xsl:variable name="dc" select="'http://purl.org/dc/terms/'" />
        <xsl:variable name="foaf" select="'http://xmlns.com/foaf/0.1/'" />
        <xsl:variable name="rdfs" select="'http://www.w3.org/2000/01/rdf-schema#'" />
<<<<<<< HEAD

=======
        
>>>>>>> 8e8ab3a8c97b248566c68017e0319dc3b7914215
        <!--Prefixes-->
        <xsl:text>@prefix dcat: &lt;</xsl:text>
        <xsl:value-of select="$dcat"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;@prefix dc: &lt;</xsl:text>
        <xsl:value-of select="$dc"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;@prefix foaf: &lt;</xsl:text>
        <xsl:value-of select="$foaf"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#x9;@prefix rdfs: &lt;</xsl:text>
        <xsl:value-of select="$rdfs"/>
        <xsl:text  >&gt; .&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
<<<<<<< HEAD
        
=======
        <!--Distribution-->
>>>>>>> 8e8ab3a8c97b248566c68017e0319dc3b7914215
        <xsl:apply-templates select="family/service" />      
    </xsl:template>
    
    <xsl:template match="family/service">
        <xsl:variable name="languageTag" select="'@en'" />
        <xsl:text>&lt;</xsl:text>  
        <xsl:value-of select="@uri" /> 
        <xsl:text>&gt;</xsl:text>    
        <xsl:text>&#10;&#x9;a dcat:Distribution ;&#10;&#x9;dc:description "</xsl:text>
        <xsl:apply-templates select="desc" />      
        <xsl:text>"</xsl:text>
        <xsl:value-of select="$languageTag"/> 
        <xsl:text> .&#10;&#10;</xsl:text>       
        
                  
    </xsl:template>

</xsl:stylesheet>
