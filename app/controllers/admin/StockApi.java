package controllers.admin;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.invoice.TransApprovals;
import controllers.returnClass;
import models.Stock;
import models.StockModelApi;
import models.temporal.StockModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Result;
import utils.AuthManager;

import java.util.List;

import static controllers.admin.ContactApi.padStr;
import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by cangulse on 10.06.15.
 */
public class StockApi {
    private final static Logger log = LoggerFactory.getLogger(TransApprovals.class);


    public static Result isStockHave(String code) {

        List<Stock> stocklst = StockModelApi.find.where().ieq("code",code).findList();
        returnClass ret = new returnClass();
        ret.sonuc = stocklst.isEmpty()?"-1":"1";
        return  ok(Json.toJson(ret));


    }


    public static Result addUpdateStock() {
        returnClass ret = new returnClass();
    try{


            JsonNode json = request().body().asJson();
            if(json == null) {
                return badRequest("Expecting Json data");
            }

            String username =  json.findPath("username").asText();
            String password =  json.findPath("password").asText();
            String code =  json.findPath("code").asText();
            code =  padStr(code,"0",3);

            String name = json.findPath("name").asText();
            Double sellPrice = json.findPath("sellPrice").asDouble();
            Double tax = json.findPath("tax").asDouble();

            String result = AuthManager.authenticate(username, password);
            List<Stock> stocklst = StockModelApi.find.where().ieq("code",code).findList();

            Stock stk;

            if (stocklst.isEmpty()){
                stk = new Stock();
            }else{
                stk = stocklst.get(0);
            }

            stk.code = code;
            stk.name = name;
            stk.sellPrice = sellPrice;
            stk.excCode = "TL";
            stk.unit1 = "ADET";
            stk.buyTax = tax;
            stk.sellTax = tax;

            stk.isActive = true;
            stk.save();;

            ret.sonuc=stk.code;
            ret.mesaj="OK";

            return  ok(Json.toJson(ret));
    }catch (Exception ex){
        log.error(ex.getMessage());

        ret.sonuc="-1";
        ret.mesaj="Fail";
        return  ok(Json.toJson(ret));
    }
    }


}
