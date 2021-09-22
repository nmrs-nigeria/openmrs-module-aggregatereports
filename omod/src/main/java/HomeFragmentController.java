import javax.servlet.http.HttpServletRequest;
import org.openmrs.ui.framework.fragment.FragmentModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author lordmaul
 */
public class HomeFragmentController {
	
	public void controller(FragmentModel model, HttpServletRequest request) {
		
		model.addAttribute("page", "index");
	}
}
