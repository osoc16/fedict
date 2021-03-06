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
        <xsl:variable name="languageTag" select="'@en'" />
        
        <xsl:variable select="@baseURI" name="catalog"/>
        <xsl:variable name="language" select="'http://publications.europa.eu/resource/authority/language/ENG'" />
        <xsl:variable name="homepage" select="'http://registry.fsb.belgium.be/web/service-catalog/partner/homepage'" />
        <xsl:variable name="publisher" select="'https://opencorporates.com/companies/be/0367302178'" />

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
        
        <!--Catalog-->
        <xsl:text>&lt;</xsl:text>
        <xsl:value-of select="$catalog"/>
        <xsl:text>&gt;&#10;</xsl:text>
        <xsl:text>&#x9;a dcat:Catalog ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:title "The FSB web service catalogue"</xsl:text>
        <xsl:value-of select="$languageTag"/> 
        <xsl:text>;&#10;</xsl:text>
        <xsl:text>&#x9;dc:description "Fedict provides to the administrations and the public a platform allowing the consultation and a standardized data exchange, from one application to another application, through the Internet. The Federal Service Bus offers a safe and secured access to the Web Services connected, among others, to authentic sources. For more details about: - The FSB - The access procedure to the Web Services; - The site navigation; Please consult the list of available Web Services by using the search tool or browse through the catalogue. Please note that this site contains only information and technical documents. The content of this site is only available in English."</xsl:text>
        <xsl:value-of select="$languageTag"/> 
        <xsl:text>;&#10;</xsl:text>
        <!--<xsl:text>&#x9;dc:issued "</xsl:text>   
        
        <xsl:text>" ;&#10;</xsl:text>
        -->
        <xsl:text>&#x9;dc:language &lt;</xsl:text>
        <xsl:value-of select="$language"/>
        <xsl:text  >&gt; ;&#10;</xsl:text>
        <xsl:text>&#x9;foaf:homepage &lt;</xsl:text>
        <xsl:value-of select="$homepage"/>
        <xsl:text  >&gt; ;&#10;</xsl:text>
        <!--<xsl:text>&#x9;dc:license ... ;&#10;</xsl:text>-->
        <xsl:text>&#x9;dc:publisher &lt;</xsl:text>
        <xsl:value-of select="$publisher"/>
        <xsl:text  >&gt; ;&#10;</xsl:text>
        <xsl:text>&#x9;dc:modified "</xsl:text>   
        <xsl:value-of select="current-date()"/> 
        <xsl:text>" ;&#10;</xsl:text>
        <xsl:text>&#x9;dcat:dataset </xsl:text>
        <!--<xsl:value-of select="$service"/> -->
        <xsl:apply-templates select="family">
        </xsl:apply-templates>
        <xsl:text  > .&#10;</xsl:text>
        <xsl:text>&#10;</xsl:text>
        
                       
                                                     
    </xsl:template>
    <xsl:template match="family">
        <xsl:param name = "families" />
        <xsl:if test="not(position()=1)">
            <xsl:text>,</xsl:text>
        </xsl:if>
        
        <xsl:text> &lt;</xsl:text>
        <xsl:value-of select="@uri"/>
        <xsl:text>&gt;</xsl:text>           

                  
    </xsl:template>
    
    
</xsl:stylesheet>
