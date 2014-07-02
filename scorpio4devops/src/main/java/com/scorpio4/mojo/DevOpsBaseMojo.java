package com.scorpio4.mojo;

import org.apache.maven.project.MavenProject;

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4
 * User  : lee
 * Date  : 2/07/2014
 * Time  : 9:34 PM
 */
public abstract class DevOpsBaseMojo extends ScorpioMojo {

	/**
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	public MavenProject project;

	public MavenProject getProject() {
		return project;
	}

	public abstract void executeInternal() throws Exception;

}
