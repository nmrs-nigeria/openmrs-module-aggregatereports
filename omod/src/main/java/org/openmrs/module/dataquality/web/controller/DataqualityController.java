/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dataquality.web.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.module.dataquality.api.dao.Database;
import org.openmrs.ui.framework.Model;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class configured as controller using annotation and mapped with the URL of
 * 'module/dataquality/dataqualityLink.form'.
 */
@Controller("${rootrootArtifactId}.DataqualityController")
@RequestMapping(value = "module/dataquality/dataquality.form")
public class DataqualityController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	UserService userService;
	
	/** Success form view name */
	private final String VIEW = "/module/dataquality/dataquality";
	
	/**
	 * Initially called after the getUsers method to get the landing form name
	 * 
	 * @param model
	 * @return String form view name
	 */
	//@RequestMapping(method = RequestMethod.GET)
	//public String onGet( PageModel model) {
	@RequestMapping(value = { "/module/dataquality/dataquality" }, method = RequestMethod.GET)
	public PageModel onGet(PageModel model) {
		
		//initialize the database here
		Database.initConnection();
		System.out.println("This is it");
		try {
			Connection con = Database.connectionPool.getConnection();
		}
		catch (SQLException ex) {
			Logger.getLogger(DataqualityController.class.getName()).log(Level.SEVERE, null, ex);
		}
		model.addAttribute("pagedata", "index");
		return model;
		//return VIEW;
	}
	
	/**
	 * All the parameters are optional based on the necessity
	 * 
	 * @param httpSession
	 * @param anyRequestObject
	 * @param errors
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String onPost(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject,
	        BindingResult errors) {
		
		if (errors.hasErrors()) {
			// return error view
		}
		
		return VIEW;
	}
	
	/**
	 * This class returns the form backing object. This can be a string, a boolean, or a normal java
	 * pojo. The bean name defined in the ModelAttribute annotation and the type can be just defined
	 * by the return type of this method
	 */
	@ModelAttribute("users")
	protected List<User> getUsers() throws Exception {
		List<User> users = userService.getAllUsers();
		
		// this object will be made available to the jsp page under the variable name
		// that is defined in the @ModuleAttribute tag
		return users;
	}
	
}
