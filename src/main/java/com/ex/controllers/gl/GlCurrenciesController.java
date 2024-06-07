package com.ex.controllers.gl;

import com.ex.common.tools.ReportsTool;
import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.models.dto.gl.GlCurrencyRatesDTO;
import com.ex.models.dto.gl.GlCurrenciesDTO;
import com.ex.services.gl.GlCurrenciesService;
import com.ex.security.utils.AuditInfo;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/admin/currencies")
@RequiredArgsConstructor
public class GlCurrenciesController {
    private final GlCurrenciesService glCurrenciesService;
    @GetMapping(value = "/currenciesList")
    public String getAllCurrencies(Model model) {
        model.addAttribute("currencies", glCurrenciesService.getAllCurrencies());
        return "views/currencies/currenciesList";
    }
    //-------------------------------------------------------------------
    @GetMapping("/createCurrency")
    public String viewCreateCurrencyForm(Model model, @ModelAttribute GlCurrenciesDTO glCurrencyDTO) {
        model.addAttribute("currencyDTO", glCurrencyDTO);
        return "views/currencies/createCurrency";
    }

    @PostMapping(value = "/createCurrency")
    public String createCurrency(@ModelAttribute GlCurrenciesDTO currencyDTO, RedirectAttributes redirectAttributes) {
        ServiceResponse response = glCurrenciesService.createCurrency(currencyDTO);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/currencies/currenciesList";
        }
        else {
            redirectAttributes.addFlashAttribute("glCurrencyDTO", currencyDTO);
            return "redirect:/admin/currencies/createCurrency";
        }
    }
    //-------------------------------------------------------------------
    @GetMapping("/updateCurrency/{currCode}")
    public String viewUpdateCurrencyForm(@PathVariable String currCode, Model model) {
        GlCurrenciesDTO currencyDTO =  glCurrenciesService.getCurrencyByCurrCode(currCode);
        model.addAttribute("currencyDTO", currencyDTO);
        return "views/currencies/updateCurrency";
    }

    @PostMapping("/updateCurrency")
    public String updateCurrency(@ModelAttribute GlCurrenciesDTO currencyDTO, RedirectAttributes redirectAttributes) {
        ServiceResponse response = glCurrenciesService.updateCurrency(currencyDTO);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/currencies/currenciesList";
        }
        else {
            return "redirect:/admin/currencies/updateCurrency/"+currencyDTO.getCurrCode();
        }

    }
    //-------------------------------------------------------------------
    @GetMapping("/deleteCurrency/{currCode}")
    public String deleteCurrency(@PathVariable String currCode, RedirectAttributes redirectAttributes) {
        ServiceResponse response = glCurrenciesService.deleteCurrency(currCode);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        return "redirect:/admin/currencies/currenciesList";
    }

    //currencyRate **********************************************************
    @GetMapping(value = "/currencyRateList")
    public String getAllRates(Model model) {
        List currencyRatesList = glCurrenciesService.getAllCurrencyRates();
        model.addAttribute("currencyRatesList",currencyRatesList);
        return "views/currencies/currencyRateList";
    }

    @GetMapping(value = "/currencyRate")
    public String viewAddNewCurrencyRate(Model model, @ModelAttribute GlCurrencyRatesDTO glCurrencyRatesDTO) {
        model.addAttribute("currencyRatesDTO", glCurrencyRatesDTO);
        model.addAttribute("currencyList", glCurrenciesService.getAllCurrencies());
        return "views/currencies/createCurrencyRate";
    }

    @PostMapping(value = "/currencyRate")
    public String addNewCurrencyRate(@ModelAttribute GlCurrencyRatesDTO currencyRatesDTO, RedirectAttributes redirectAttributes) {
        ServiceResponse response = glCurrenciesService.creatCurrencyRate(currencyRatesDTO);
        redirectAttributes.addFlashAttribute(response.getMessageType(), response.getMessage());
        if(response instanceof ServiceResult) {
            return "redirect:/admin/currencies/currencyRateList";
        }
        else {
            redirectAttributes.addFlashAttribute("glCurrencyRatesDTO", currencyRatesDTO);
            return "redirect:/admin/currencies/currencyRate";
        }
    }
    //-------------------------------------------------------------------
    @GetMapping("/printCurrencyReport")
    public ResponseEntity<Resource> generateUsersReport() throws FileNotFoundException, JRException {
        try{
            List<GlCurrenciesDTO> currencies= glCurrenciesService.getAllCurrencies();
            return ReportsTool.generateReport(null,currencies,"classpath:reports/currenciesReport.jrxml","currencies");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Ajax *************************************************************************
    @GetMapping("/get-currency-rate")
    @ResponseBody
    public GlCurrencyRatesDTO getCurrencyRate(@RequestParam("fromCurrCode") String fromCurrCode,@RequestParam("voucherDate") String voucherDate) {
        String mainCurrencyCode = AuditInfo.getAuthUser().getMainCurrency().getCurrCode();
        GlCurrencyRatesDTO ratesDTO = glCurrenciesService.getCurrencyRateFromToCurrCode(fromCurrCode,mainCurrencyCode,voucherDate);
        return ratesDTO;
    }



//    //-------------------------------------------------------------------
//    @GetMapping(value = "/{currSign}")
//    public ResponseEntity<CurrencyResponse> getCurrencyById(@PathVariable String currSign) {
//        CurrencyResponse currency =  glCurrenciesService.getCurrencyByCurrSign(currSign);
//        return new ResponseEntity<>(currency, HttpStatus.OK);
//    }
//    //-------------------------------------------------------------------
//
//    @GetMapping(value = "admin/currencies/mainCurrency")
//    public  ResponseEntity<CurrencyResponse> getMainCurrency(){
//        CurrencyResponse mainCurrency = glCurrenciesService.getMainCurrency(true);
//        return new ResponseEntity<>(mainCurrency,HttpStatus.OK);
//    }
//    //-------------------------------------------------------------------
//
//    @GetMapping(value = "admin/currencyRate/{fromCurrSign}/{toCurrSign}")
//    public ResponseEntity<CurrencyRateResponse> getCurrencyRate(@PathVariable String fromCurrSign, @PathVariable String toCurrSign) {
//        CurrencyRateResponse currencyRateResponse = glCurrenciesService.getCurrencyRateFromToCurrSign(fromCurrSign,toCurrSign);
//        return new ResponseEntity<>(currencyRateResponse, HttpStatus.OK);
//    }
//    //-------------------------------------------------------------------
}
