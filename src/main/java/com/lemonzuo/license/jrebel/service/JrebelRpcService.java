package com.lemonzuo.license.jrebel.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.lemonzuo.license.common.service.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author LemonZuo
 * @create 2024-05-16 23:43
 */
@Service
public class JrebelRpcService implements RpcService {
    private static final String PRIVATE_KEY_STR = """
        MIIBOgIBAAJBALecq3BwAI4YJZwhJ+snnDFj3lF3DMqNPorV6y5ZKXCiCMqj8OeOmxk4YZW9aaV9
        ckl/zlAOI0mpB3pDT+Xlj2sCAwEAAQJAW6/aVD05qbsZHMvZuS2Aa5FpNNj0BDlf38hOtkhDzz/h
        kYb+EBYLLvldhgsD0OvRNy8yhz7EjaUqLCB0juIN4QIhAOeCQp+NXxfBmfdG/S+XbRUAdv8iHBl+
        F6O2wr5fA2jzAiEAywlDfGIl6acnakPrmJE0IL8qvuO3FtsHBrpkUuOnXakCIQCqdr+XvADI/UTh
        TuQepuErFayJMBSAsNe3NFsw0cUxAQIgGA5n7ZPfdBi3BdM4VeJWb87WrLlkVxPqeDSbcGrCyMkC
        IFSs5JyXvFTreWt7IQjDssrKDRIPmALdNjvfETwlNJyY
        """;

    private String sign(String content) {
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, PRIVATE_KEY_STR, null);
        byte[] signature = sign.sign(content);
        return Base64.encode(sign.sign(signature));
    }

    @Override
    public String ping(String machineId, String salt) {
        String xmlContent = "<PingResponse><message></message><responseCode>OK</responseCode><salt>%s</salt></PingResponse>";
        xmlContent = String.format(xmlContent, salt);
        String xmlSignature = sign(xmlContent);
        return String.format("<!-- %s -->\n%s", xmlSignature, xmlContent);
    }

    @Override
    public String obtainTicket(String username, String hostName, String machineId, String salt) {
        String prolongationPeriod = "607875500";
        String xmlContent = "<ObtainTicketResponse><message></message><prolongationPeriod>" + prolongationPeriod + "</prolongationPeriod><responseCode>OK</responseCode><salt>" + salt + "</salt><ticketId>1</ticketId><ticketProperties>licensee=" + username + "\tlicenseType=0\t</ticketProperties></ObtainTicketResponse>";
        String xmlSignature = sign(xmlContent);
        return "<!-- " + xmlSignature + " -->\n" + xmlContent;
    }

    @Override
    public String releaseTicket(String machineId, String salt) {
        String xmlContent = "<ReleaseTicketResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></ReleaseTicketResponse>";
        String xmlSignature = sign(xmlContent);
        return "<!-- " + xmlSignature + " -->\n" + xmlContent;
    }
}
