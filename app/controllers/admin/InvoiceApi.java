package controllers.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import controllers.invoice.TransApprovals;
import controllers.returnClass;
import enums.Right;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Result;
import utils.AuthManager;

import java.util.ArrayList;

import static controllers.admin.ContactApi.padStr;
import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by cangulse on 10.06.15.
 */
class gelenStock{
    String name;
    Double quantity;
    Double price;
    Double amount;
    Double discount;
    Double discount_amount;
    Double tax_rate;
    Double tax_amount;
    Double total;
    String descriptiondet;
}

public class InvoiceApi {
    private final static Logger log = LoggerFactory.getLogger(TransApprovals.class);
    public static Result addInvoice() {


        returnClass ret = new returnClass();
            try {
                JsonNode json = request().body().asJson();
                if(json == null) {
                    return badRequest("Expecting Json data");
                }
                String username = json.findPath("username").asText();
                String password = json.findPath("password").asText();
                String result = AuthManager.authenticate(username, password);
                Integer receiptNo = json.findPath("receiptNo").asInt();
                String description = json.findPath("description").asText();
                String stks  = json.findPath("stks").asText();
                String contCode = json.findPath("contCode").asText();
                contCode = padStr(contCode,"0",3);
                String contactName = json.findPath("contactName").asText();
                String contactTaxOffice = json.findPath("contactTaxOffice").asText();
                String contactTaxNumber= json.findPath("contactTaxNumber").asText();
                String contactAddress1 = json.findPath("contactAddress1").asText();
                String contactAddress2  = json.findPath("contactAddress2").asText();


                log.info("Burdayım Ordayım");

                log.info(stks);
                JsonNode stkj = Json.parse(stks);
                ArrayNode stk = (ArrayNode)stkj;
                InvoiceTrans inv = new InvoiceTrans();
                inv.receiptNo = receiptNo;
                inv.transNo = receiptNo.toString();
                inv.transDate = new java.util.Date();
                inv.description = description;

                //Stok Bilgisi
                inv.details = new ArrayList<InvoiceTransDetail>();
                log.info("Burdayım Ordayım1 RecNo"+receiptNo);

                for (JsonNode st : stk) {
                    InvoiceTransDetail detail = new InvoiceTransDetail();
                    String stockCode  = st.get("stockCode").asText();
                    stockCode = padStr(stockCode,"0",3);
                    log.info(stockCode);
                    Stock stok = StockModelApi.find.where().ieq("code",stockCode).findList().get(0);
                    detail.stock =  stok;

                    log.info("Burdayım Ordayım Stok : "+stok.name);
                    detail.name = st.get("name").asText();
                    log.info("1");
                    detail.quantity = st.get("quantity").asDouble();
                    log.info("2");
                    detail.price = st.get("price").asDouble();
                    log.info("3");
                    detail.amount = st.get("amount").asDouble();
                    log.info("4");
                    detail.discountRate1 = st.get("discount").asDouble();
                    log.info("5");
                    detail.discountAmount =  st.get("discount_amount").asDouble();
                    log.info("6");
                    detail.taxRate = st.get("tax_rate").asDouble();
                    log.info("7");
                    detail.taxAmount = st.get("tax_amount").asDouble();
                    log.info("8");
                    detail.total = st.get("price").asDouble();;
                    log.info("9");
                    detail.receiptNo =  receiptNo;
                    detail.right = Right.FATR_SATIS_FATURASI;
                    detail.workspace = 1;
                    detail.unit = "ADET";
                    detail.unit1 = "ADET";
                    inv.details.add(detail);

                    log.info("Burdayım Ordayım loop");

                }

                log.info("Burdayım Ordayım Contact Code : "+contCode);

                inv.contactName = contactName;
                Contact cont = ContactModelApi.find.where().ieq("code",contCode).findList().get(0);
                log.info("Burdayım Ordayım Contact Name: "+cont.name);
                inv.contact = cont;
                inv.contactTaxOffice = contactTaxOffice;
                inv.contactTaxOffice = contactTaxNumber;
                inv.contactAddress1 = contactAddress1;
                inv.contactAddress2 = contactAddress2;
                inv.isCash = false;
                inv.right = Right.FATR_SATIS_FATURASI;
                inv.save();

                ret.sonuc=inv.id.toString();
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
