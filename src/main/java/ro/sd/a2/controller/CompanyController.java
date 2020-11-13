package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.entity.Company;
import ro.sd.a2.entity.User;
import ro.sd.a2.service.CompanyService;
import ro.sd.a2.service.ValuteService;

import java.util.UUID;

/**
 * Controller class to process all the requests of admin on companies.
 */
@Controller
@RequestMapping("/admin/companies")
public class CompanyController {
    private static final Logger log = LoggerFactory.getLogger(FirstController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ValuteService valuteService;


    /**
     * Method to process the request of seeing new companies.
     */
    @GetMapping("")
    public ModelAndView seeCompanies( @RequestParam(value = "succMsg", required = false) String succMsg)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("companies");
        mav.addObject("companies", companyService.findAll());
        mav.addObject("valutes", valuteService.findAll());
        mav.addObject("company", new Company());
        if(succMsg!=null) mav.addObject("succ", true).addObject("succMsg", succMsg);
        log.info("ALL GOOD");
        return mav;
    }

    /**
     * Method of adding a new company in the database.
     */
    @PostMapping("")
    public ModelAndView postCompany(@ModelAttribute(name="company") Company company)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/admin/companies");
        mav.addObject("succMsg", "Added company");
        company.setId(UUID.randomUUID().toString());
        companyService.insert(company);
        return mav;
    }
}
