package controllers.admin;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.returnClass;
import models.Contact;
import models.ContactCategory;
import models.ContactModelApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.AuthManager;

import java.util.List;

/**
 * Created by cangulse on 7.06.15.
 */
public class ContactApi  extends Controller {


    /**
     * Contact Id araması yapılarak var / yok bilgisi geri dönülür.
     *
     * @param code
     */


    public static Result isContactHave(String code) {

//        code = String.format("%03s", code);
        code =  padStr(code,"0",3);
        List<Contact> modellst = ContactModelApi.find.where().ieq("code",code).findList();

        returnClass ret = new returnClass();
        if (modellst.isEmpty()) {
            ret.sonuc = "-1";
            return ok(Json.toJson(ret));
        }else{
            ret.sonuc = "1";
            return ok(Json.toJson(ret));
        }


    }


    public static String padStr(String val, String pad, int len) {
        String str = val;
        while (str.length() < len)
            str = pad + str;
        return str;
    }
    /**
     * Contact Ekle/Güncelle Metodu
     *
     *
     */
    public static Result addUpdateContact() {
        returnClass ret = new returnClass();
        JsonNode json = request().body().asJson();
        if(json == null) {
            return badRequest("Expecting Json data");
        }
        System.out.print("Başlıoo");
        String username = json.findPath("username").asText();
        String password = json.findPath("password").asText();
        String result = AuthManager.authenticate(username, password);

        String code = json.findPath("code").asText();
        String name = json.findPath("name").asText();
        String taxOffice = json.findPath("taxOffice").asText();
        String taxNumber= json.findPath("taxNumber").asText();
        String tcKimlik = json.findPath("tcKimlik").asText();
        String phone  = json.findPath("phone").asText();
        String fax  = json.findPath("fax").asText();
        String mobilePhone = json.findPath("mobilePhone").asText();
        String address1 = json.findPath("address1").asText();
        String address2 = json.findPath("address2").asText();
        String city = json.findPath("city").asText();
        String email = json.findPath("email").asText();
        String website = json.findPath("website").asText();
        Contact model;
        code =  padStr(code, "0", 3);
        List<Contact> modellst = ContactModelApi.find.where().ieq("code",code).findList();
        if (modellst.isEmpty()) {
            model = new Contact();
        }else
        {
            model = modellst.get(0);
        }
        model.code = code;
        model.name = name;
        model.taxOffice = taxOffice;
        model.taxNumber = taxNumber;
        model.tcKimlik = tcKimlik;
        model.phone = phone;
        model.fax = fax;
        model.mobilePhone = mobilePhone;
        model.address1 = address1;
        model.address2 = address2;
        model.city = city;
        model.email = email;
        model.website = website;
        model.category = ContactCategory.findById(3);
        model.save();

        ret.sonuc=model.id.toString();
        ret.mesaj="OK";
        return  ok(Json.toJson(ret));

    }

}
