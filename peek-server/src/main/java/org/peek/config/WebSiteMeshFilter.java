package org.peek.config;



import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.DivExtractingTagRuleBundle;


public class WebSiteMeshFilter extends ConfigurableSiteMeshFilter{  
	
  @Override   
  protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {  
	  
     builder.addDecoratorPath("/*", "/layout.html").addExcludedPath("/monitor/*")
     .addExcludedPath("/api/*").addExcludedPath("/detail/*")
     .addExcludedPath("/login/*").addExcludedPath("/peekWeixin/*"); ;       
   builder.addTagRuleBundles(new DivExtractingTagRuleBundle());  
  }
}