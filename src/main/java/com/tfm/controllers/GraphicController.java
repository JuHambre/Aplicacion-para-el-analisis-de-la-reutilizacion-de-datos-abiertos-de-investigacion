package com.tfm.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GraphicController {

	@RequestMapping(value="/getGraphics", method=RequestMethod.GET)
	public String getGraphics(Model modelo)
	{
		
		ArrayList <Object> list = new ArrayList <Object> ();
		ArrayList <ArrayList> listArray = new ArrayList <ArrayList> ();
		list.add("Task");
		list.add("Hours per Day");
		ArrayList <Object> list1 = new ArrayList <Object> ();
		ArrayList <Object> list2 = new ArrayList <Object> ();
		list1.add("CrossRef: 857");
		list1.add(857);
		list2.add("Twitter: 2");
		list2.add(2);
		listArray.add(list);
		listArray.add(list1);
		listArray.add(list2);
		
		
		//modelo.addAttribute("prueba1", "['Task', 'Hours per Day']|['Work', 11]|['Eat', 2]|['Commute', 2]|['Watch TV', 2]|['Sleep', 7]");
		modelo.addAttribute("prueba1", listArray);
		return "graphic";
	}
}
