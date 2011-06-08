<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	version="2.0"
	xmlns:def="http://vespucci.editor"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xmi="http://www.omg.org/XMI"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.2/notation"
	exclude-result-prefixes="def">

	<xsl:output method="xml" intend="yes" />

	<xsl:template match="xmi:XMI">
		<xmi:XMI xmlns="http://vespucci.editor/2011-06-01">
			<xsl:apply-templates select="def:ShapesDiagram" />
			<xsl:apply-templates select="notation:Diagram" />
		</xmi:XMI>
	</xsl:template>

	<xsl:template match="def:ShapesDiagram">
		<ShapesDiagram>
			<xsl:for-each select="@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>

			<xsl:apply-templates select="def:shapes" />
		</ShapesDiagram>
	</xsl:template>

	<xsl:template match="def:shapes">
		<shapes>
			<xsl:for-each select="@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>
			<xsl:apply-templates select="def:targetConnections" />
			<xsl:apply-templates select="def:shapes" />
		</shapes>
	</xsl:template>

	<xsl:template match="def:targetConnections">
		<shapes>
			<xsl:for-each select="@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>
		</shapes>
	</xsl:template>
	
	<xsl:template match="notation:Diagram">
		<notation:Diagram>
			<xsl:for-each select="@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>
			
			<xsl:apply-templates select="def:children[@xmi:type='notation:Shape' and @type='2001']" />
			<xsl:apply-templates select="def:children[not(@xmi:type='notation:Shape' and @type='2001')]" />
		</notation:Diagram>
	</xsl:template>
	
	<!-- ENSEMBLE-Template -->
	<xsl:template match="def:children[@xmi:type='notation:Shape' and @type='2001']">
		<children>
			<xsl:for-each select="@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>
			
			<!-- previous existing, first decoration node -->
			<children>
				<xsl:for-each select="def:children[@xmi:type='notation:DecorationNode' and @type='5001']/@*">
					<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
				</xsl:for-each>
			</children>
			
			<!-- CHANGE: additional decoration node -->
			<children xmi:type="notation:DecorationNode" type="5008">
				<xsl:attribute name="xmi:id">
					<xsl:value-of select="generate-id(def:children[@xmi:type='notation:DecorationNode'])" />
				</xsl:attribute>
			</children>
		</children>
	</xsl:template>
	
	<!-- Template for all other notation:Shape elements -->
	<xsl:template match="def:children[not(@xmi:type='notation:Shape' and @type='2001')]">
		<children>
			<xsl:for-each select="@*">
				<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
			</xsl:for-each>
			
			<xsl:call-template name="RecursiveDuplicate">
			</xsl:call-template>
		</children>
	</xsl:template>
	
	<!-- Template which recursively traverses over the children of the current node -->
	<xsl:template name="RecursiveDuplicate">
		<xsl:for-each select="./*">
			<xsl:element name="{name()}">
				<xsl:for-each select="@*">
					<xsl:attribute name="{name()}"><xsl:value-of select="." /></xsl:attribute>
				</xsl:for-each>
				
				<xsl:apply-templates select="./*" />
			</xsl:element>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>