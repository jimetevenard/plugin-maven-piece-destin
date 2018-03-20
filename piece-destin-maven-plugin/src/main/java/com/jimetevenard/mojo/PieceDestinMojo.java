package com.jimetevenard.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "piece")
public class PieceDestinMojo extends AbstractMojo{

	public void execute() throws MojoExecutionException, MojoFailureException {
		if(Math.random() > 0.5 ){
			getLog().info("Ca build, va au resto poto !");
		} else {
			MojoFailureException ex = new MojoFailureException("Pas de chance ! Plat végé au micro-ondes");
			getLog().error(ex);
			throw ex;
		}		
	}
	
}
