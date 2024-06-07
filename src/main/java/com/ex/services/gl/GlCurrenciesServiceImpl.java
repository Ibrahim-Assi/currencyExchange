package com.ex.services.gl;

import com.ex.common.tools.DateTool;
import com.ex.common.tools.ValidateTool;
import com.ex.models.dto.gl.GlCurrencyRatesDTO;
import com.ex.models.entities.gl.GlCurrencies;
import com.ex.models.dto.gl.GlCurrenciesDTO;
import com.ex.models.entities.gl.GlCurrencyRates;
import com.ex.models.entities.gl.GlCurrencyRatesPK;
import com.ex.repositories.gl.GlCurrenciesRepository;
import com.ex.repositories.gl.GlCurrencyRatesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ex.exceptions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GlCurrenciesServiceImpl implements GlCurrenciesService {

    private final GlCurrenciesRepository currenciesRepository;
    private final GlCurrencyRatesRepository currencyRatesRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<GlCurrenciesDTO> getAllCurrencies() {
       List<GlCurrencies> systemCurrenciesList = currenciesRepository.findAll(Sort.by( Sort.Order.asc("currArName")));
        if (ValidateTool.isEmpty(systemCurrenciesList))
           return null;

        ModelMapper modelMapper = new ModelMapper();
        return systemCurrenciesList.stream()
                .map(currency -> modelMapper.map(currency, GlCurrenciesDTO.class))
                .collect(Collectors.toList());
    }
    //---------------------------------------------------------------------------------------------

    @Override
    public GlCurrenciesDTO getCurrencyByCurrCode(String currCode) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        GlCurrencies currency = currenciesRepository.findByCurrCode(currCode);
        if(currency == null)
            throw new ServiceException("العملة غير موجودة "+currCode);

        GlCurrenciesDTO currenciesDTO = modelMapper.map(currency, GlCurrenciesDTO.class);
        return currenciesDTO;
    }
    //---------------------------------------------------------------------------------------------

    @Override
    public ServiceResponse createCurrency(GlCurrenciesDTO currencyDTO) {

        GlCurrencies glCurrencies = currenciesRepository.findByCurrCode(currencyDTO.getCurrCode());
        if(glCurrencies != null)
            return new ServiceException("العملة تم ادخالها سابقا");

        ModelMapper modelMapper = new ModelMapper();
        GlCurrencies currency = modelMapper.map(currencyDTO, GlCurrencies.class);
        currenciesRepository.save(currency);

        return new ServiceResult("تم اضافة العملة بنجاح");
    }
    //---------------------------------------------------------------------------------------------

    @Override
    public ServiceResponse updateCurrency(GlCurrenciesDTO currencyDTO) {
        // for check
        GlCurrencies currencyDb = currenciesRepository.findByCurrCode(currencyDTO.getCurrCode());
        if(currencyDb == null)
            return  new ServiceException("رمز العملة غير صحيح" + currencyDTO.getCurrCode());
        // update
        ModelMapper modelMapper = new ModelMapper();
        GlCurrencies currency = modelMapper.map(currencyDTO, GlCurrencies.class);
        currenciesRepository.save(currency);
        return new ServiceResult("تم تعديل العملة بنجاح");
    }
    //---------------------------------------------------------------------------------------------

    @Override
    public ServiceResponse deleteCurrency(String currCode) {
        GlCurrencies currency = currenciesRepository.findByCurrCode(currCode);
        if(currency == null)
            return  new ServiceException("رمز العملة غير صحيح" + currCode);
        currenciesRepository.delete(currency);
        return new ServiceResult("تم حذف العملة بنجاح");
    }
    //---------------------------------------------------------------------------------------------

    @Override
    public List<GlCurrencyRatesDTO> getAllCurrencyRates() {
        List<GlCurrencyRates> currencyRatesList = currencyRatesRepository.findAll(Sort.by( Sort.Order.desc("id.rateDate")));
        if(currencyRatesList.size() ==0)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        Map<String, String> currencyNameMap = currenciesRepository.findAll().stream().collect(Collectors.toMap(GlCurrencies::getCurrCode, GlCurrencies::getCurrArName));
        modelMapper.addMappings(new PropertyMap<GlCurrencyRates, GlCurrencyRatesDTO>() {
            @Override
            protected void configure() {
                map().setFromCurrCode(source.getId().getFromCurrCode());
                map().setToCurrCode(source.getId().getToCurrCode());
                map().setRateDate(source.getId().getRateDate());
            }
        });

        List<GlCurrencyRatesDTO> currencyRatesDTOList = new ArrayList<>();
        for(GlCurrencyRates currencyRates:currencyRatesList){
            GlCurrencyRatesDTO currencyRateResponse = modelMapper.map(currencyRates, GlCurrencyRatesDTO.class);
            currencyRateResponse.setFromCurrName(currencyNameMap.get(currencyRates.getId().getFromCurrCode()));
            currencyRateResponse.setToCurrName(currencyNameMap.get(currencyRates.getId().getToCurrCode()));
            currencyRatesDTOList.add(currencyRateResponse);
        }
        return currencyRatesDTOList;
    }
    //---------------------------------------------------------------------------------------------

    @Override
    public GlCurrencyRatesDTO getCurrencyRateFromToCurrCode(String fromCurrCode, String toCurrCode,String voucherDate) {
        GlCurrencies fromCurr = currenciesRepository.findByCurrCode(fromCurrCode);
        GlCurrencies toCurr = currenciesRepository.findByCurrCode(toCurrCode);
        Date vDate = DateTool.convertStringToDate(voucherDate+" 23:59","dd/MM/yyyy HH:mm");

        if(fromCurr == null || toCurrCode == null)
            throw new ServiceException("Invalid CurrCode");
        else if(fromCurrCode.equals(toCurrCode))
            return new GlCurrencyRatesDTO(1.0,1.0,1.0);

        Query query = this.em.createQuery(" SELECT rate FROM GlCurrencyRates rate " +
                                             " WHERE " +
                                             " rate.id.fromCurrCode =:fromCurrCode and " +
                                             " rate.id.toCurrCode =:toCurrCode and " +
                                             " rate.id.rateDate <=:vDate " +
                                             " order by rate.id.rateDate desc");
        query.setParameter("fromCurrCode", fromCurr.getCurrCode());
        query.setParameter("toCurrCode", toCurr.getCurrCode());
        query.setParameter("vDate", vDate);

        List resultList  = query.getResultList();
        if(ValidateTool.isEmpty(resultList))
            return null;

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<GlCurrencyRates, GlCurrencyRatesDTO>() {
            @Override
            protected void configure() {
                map().setFromCurrCode(source.getId().getFromCurrCode());
                map().setToCurrCode(source.getId().getToCurrCode());
                map().setRateDate(source.getId().getRateDate());
            }
        });


        GlCurrencyRates currencyRates =  (GlCurrencyRates) resultList.get(0);
        return modelMapper.map(currencyRates, GlCurrencyRatesDTO.class);
    }
    //---------------------------------------------------------------------------------------------

    @Override
    public List<GlCurrencyRatesDTO> getAllCurrencyLastRateList() {

        Query query = this.em.createQuery("SELECT rate" +
                                             " FROM GlCurrencyRates rate" +
                                             " JOIN (" +
                                             "    SELECT r.id.fromCurrCode AS fromCurrCode, r.id.toCurrCode AS toCurrCode, MAX(r.id.rateDate) AS maxRateDate" +
                                             "    FROM GlCurrencyRates r" +
                                             "    GROUP BY r.id.fromCurrCode, r.id.toCurrCode" +
                                             " ) maxDates" +
                                             " ON rate.id.fromCurrCode = maxDates.fromCurrCode" +
                                             " AND rate.id.toCurrCode = maxDates.toCurrCode" +
                                             " AND rate.id.rateDate = maxDates.maxRateDate");

        List<GlCurrencyRates> resultList  = query.getResultList();
        if(ValidateTool.isEmpty(resultList))
            return null;

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<GlCurrencyRates, GlCurrencyRatesDTO>() {
            @Override
            protected void configure() {
                map().setFromCurrCode(source.getId().getFromCurrCode());
                map().setToCurrCode(source.getId().getToCurrCode());
                map().setRateDate(source.getId().getRateDate());
            }
        });

        return resultList.stream()
                .map(currencyRate -> modelMapper.map(currencyRate, GlCurrencyRatesDTO.class))
                .collect(Collectors.toList());
    }

    //---------------------------------------------------------------------------------------------
    @Override
    public ServiceResponse creatCurrencyRate(GlCurrencyRatesDTO currencyRatesDTO) {

        if(currencyRatesDTO.getFromCurrCode().equals(currencyRatesDTO.getToCurrCode()))
            throw new ServiceException("لا يمكن اضافة سعر صرف للعملة نفسها") ;
        else if(currencyRatesDTO.getPurchaseRate() > currencyRatesDTO.getSaleRate())
            return  new ServiceException("لا يمكن ان يكون سعر الشراء اكبر من سعر البيع");

        GlCurrencies fromCurr = currenciesRepository.findByCurrCode(currencyRatesDTO.getFromCurrCode());
        GlCurrencies toCurr = currenciesRepository.findByCurrCode(currencyRatesDTO.getToCurrCode());

        if(fromCurr == null || toCurr == null)
            throw new ServiceException("رمز العملة غير صحيح");

        GlCurrencyRatesPK glCurrencyRatesPK = new GlCurrencyRatesPK(fromCurr.getCurrCode(), toCurr.getCurrCode(), new Date());
        GlCurrencyRates glCurrencyRates = new GlCurrencyRates(glCurrencyRatesPK, currencyRatesDTO.getSaleRate(), currencyRatesDTO.getPurchaseRate(), currencyRatesDTO.getAvgRate());
        currencyRatesRepository.save(glCurrencyRates);

        return new ServiceResult("تم اضافة سعر الصرف بنجاح");
    }
    //---------------------------------------------------------------------------------------------


}
