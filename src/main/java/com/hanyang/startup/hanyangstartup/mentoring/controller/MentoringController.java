package com.hanyang.startup.hanyangstartup.mentoring.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.auth.service.AuthService;
import com.hanyang.startup.hanyangstartup.common.domain.Email;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.common.exception.CustomException;
import com.hanyang.startup.hanyangstartup.common.service.EmailService;
import com.hanyang.startup.hanyangstartup.common.util.EncodingUtil;
import com.hanyang.startup.hanyangstartup.mentoring.domain.*;
import com.hanyang.startup.hanyangstartup.mentoring.service.MentoringService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mentoring")
public class MentoringController {

    @Autowired
    private MentoringService mentoringService;
    @Autowired
    private AuthService authService;

    @Autowired
    private EncodingUtil encodingUtil;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TemplateEngine templateEngine;

    @Value(value = "${config.adminEmail}")
    private String ADMIN_EMAIL;


    @GetMapping("/counsel_field_code")
    public ResponseEntity<Response> getCounselFieldCode(){
        Response response;
        try {
            List<CounselFiledCode> counselFiledCodeList = mentoringService.getCounselFieldCode();

            response = new Response("success", null, counselFiledCodeList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping("/progress_item")
    public ResponseEntity<Response> getProgressItem(){
        Response response;
        try {
            List<ProgressItem> progressItemList = mentoringService.getProgressItem();

            response = new Response("success", null, progressItemList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/sortation_item")
    public ResponseEntity<Response> getSortationItem(){
        Response response;
        try {
            List<SortationItem> sortationItemList = mentoringService.getSortationItem();

            response = new Response("success", null, sortationItemList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/way_item")
    public ResponseEntity<Response> getWayItem(){
        Response response;
        try {
            List<WayItem> wayItemList = mentoringService.getWayItem();

            response = new Response("success", null, wayItemList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/mentor/check")
    public ResponseEntity<Response> getMentorCheck(Principal principal){
        Response response;
        try {
            Mentor mentor = new Mentor();
//            mentor.setUserId(principal.getName());
            mentor.setUserId(principal.getName());

            if(mentoringService.getMentor(mentor) != null){
                response = new Response("success", null, null, 409);

            }else{
                response = new Response("success", null, null, 200);
            }

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/mentor")
    public ResponseEntity<Response> getMentor(Principal principal){
        Response response;
        try {
            Mentor mentor = new Mentor();
            mentor.setUserId(principal.getName());

            response = new Response("success", null, mentoringService.getMentor(mentor), 409);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mentor/best")
    public ResponseEntity<Response> getBestMentor(Principal principal){
        Response response;
        try {
            response = new Response("success", null, mentoringService.getBestMentor(), 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mentor/list")
    public ResponseEntity<Response> getMentorList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",required = false) Integer pageSize, @RequestParam(value = "counselField", defaultValue = "") Integer counselField,Principal principal){
        Response response;
        try {

            Mentor mentor = new Mentor();
            mentor.setMentorStatus(MENTOR_STATUS.ACCEPT);
            mentor.setPageNo(page);
            if(pageSize == null){
                mentor.setPageSize(9);
            }else{
                mentor.setPageSize(pageSize);
            }
            if(counselField != null){
                List<Integer> counselFieldList = new ArrayList<>();
                counselFieldList.add(counselField);
                mentor.setMentorFieldList(counselFieldList);
            }

            Map<String, Object> map = mentoringService.getMentorList(mentor);

            response = new Response("success", null, map, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/mentor/apply")
    public ResponseEntity<Response> applyMentor(Mentor mentor, Principal principal){
        Response response;
        try {

            mentor.setUserId(principal.getName());

            mentoringService.applyMentor(mentor);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/mentor/update")
    @ResponseBody
    public ResponseEntity<Response> updateMentorProfile(@ModelAttribute Mentor mentor,BindingResult bindingResult, Principal principal){
        Response response;
//        if(bindingResult.hasErrors()){
//            bindingResult.getAllErrors().forEach(v ->{
//                System.out.println(v.toString());
//            });
//        }
        try {

            mentor.setUserId(principal.getName());

            mentoringService.updateMentorProfile(mentor);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }


    @PostMapping("/counsel/apply")
    public ResponseEntity<Response> applyCounsel(CounselApplyForm counselApplyForm, Principal principal){
        Response response;
        try {

            counselApplyForm.setUserId(principal.getName());

            System.out.println("====>상담 신청");
            System.out.println(counselApplyForm);

            mentoringService.applyCounsel(counselApplyForm);

//            User user = authService.findByUserId(principal.getName());
            Email email = new Email();
            email.setToName(counselApplyForm.getMenteeName());
            email.setTo(counselApplyForm.getMenteeEmail());
            email.setTitle("멘토링신청이 접수되었습니다");
            emailService.sendEmail(email);

            Email email2 = new Email();
            email2.setToName("한양대학교 창업지원단");
            email2.setTo(ADMIN_EMAIL);
            email2.setTitle("멘토링신청이 접수되었습니다");
            emailService.sendEmail(email2);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/counsel_apply/{formId}")
    public ResponseEntity<Response> getCounselApply(@PathVariable("formId") Integer formId, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setUserId(principal.getName());
            counselApplyForm.setFormId(formId);
            Map<String, Object> map = mentoringService.getCounselApply(counselApplyForm);

            response = new Response("success", null, map, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/counsel_apply/status")
    public ResponseEntity<Response> updateCounselApplyStatus(@RequestBody CounselApplyForm counselApplyForm){
        Response response;
        try {

            mentoringService.updateCounselApplyStatus(counselApplyForm);

            response = new Response("success", null, counselApplyForm.getApplyStatus(), 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/counsel_apply/mentor/{formId}")
    public ResponseEntity<Response> getMentorCounselApply(@PathVariable("formId") Integer formId, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setMentorUserId(principal.getName());
            counselApplyForm.setFormId(formId);
            Map<String, Object> map = mentoringService.getCounselApply(counselApplyForm);

            response = new Response("success", null, map, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/counsel/apply")
    public ResponseEntity<Response> getCounselApplyList(@RequestParam(value = "page", defaultValue = "1") Integer page, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setPageNo(page);
//            counselApplyForm.setUserId(principal.getName());

            counselApplyForm.setUserId(principal.getName());

            Map<String,Object> map = mentoringService.getCounselApplyList(counselApplyForm);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/counsel_apply/mentor")
    public ResponseEntity<Response> getMentorCounselApplyList(@RequestParam(value = "page", defaultValue = "1") Integer page, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setPageNo(page);
//            counselApplyForm.setUserId(principal.getName());

            counselApplyForm.setMentorUserId(principal.getName());

            Map<String,Object> map = mentoringService.getCounselApplyList(counselApplyForm);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/counsel_apply/diary")
    public ResponseEntity<Response> addDiary(MentoringDiary mentoringDiary, Principal principal){
        Response response;
        try {

            mentoringDiary.setMentorUserId(principal.getName());

            System.out.println("====>답변 작성");
            System.out.println(mentoringDiary);

            mentoringService.addDiary(mentoringDiary);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/counsel_apply/diary")
    public ResponseEntity<Response> updateDiary(@RequestBody MentoringDiary mentoringDiary, Principal principal){
        Response response;
        try {

            mentoringDiary.setMenteeUserId(principal.getName());

            System.out.println("====>답변 수정");

            mentoringService.updateDiary(mentoringDiary);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping("/commission")
    public byte[] commissionDownload (HttpServletRequest req, HttpServletResponse res,Principal principal) throws FileNotFoundException, IOException, DocumentException {
        try{

            Mentor mentor = new Mentor();
            mentor.setUserId(principal.getName());

            mentor = (Mentor)mentoringService.getMentor(mentor).get("mentor");

            LocalDateTime localDateTime = LocalDateTime.now();
            int year = localDateTime.getYear();
            int month = localDateTime.getMonthValue();
            int day = localDateTime.getDayOfMonth();

            Map<String,Object> map = new HashMap();

            map.put("name",mentor.getMentorName());
            map.put("company",mentor.getMentorCompany());
            map.put("position",mentor.getMentorPosition());
            map.put("date",year + "년 "+month+"월 "+day+"일");


            String fileName = mentor.getMentorName()+"_위촉장.pdf";

        fileName = encodingUtil.browserFileNameEncoding(fileName, encodingUtil.getBrowser(req));

        Context context = new Context();
        context.setVariable("info", map);

        String process = templateEngine.process("commission/commission", context);

        ClassPathResource classPathResource = new ClassPathResource("templates/commission/style.css");
        if(classPathResource.exists() == false){
            throw new IllegalArgumentException();
        }
//            new InputStreamReader(classPathResource.getInputStream(), "UTF-8")
//            String scss = new ClassPathResource("templates/commission/style.css").getURL().getPath();
        String sfont = Paths.get("/root/lib/font.ttf").toString();


        Document document = new Document(PageSize.A4, 0, 0, 0, 0);

        PdfWriter writer = PdfWriter.getInstance(document, new BufferedOutputStream(res.getOutputStream()));

        // 파일 다운로드 설정
        res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setContentType("application/pdf");

        // Document 오픈
        document.open();
        XMLWorkerHelper helper = XMLWorkerHelper.getInstance();

        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();

        CssFile cssFile = helper.getCSS(new ClassPathResource("templates/commission/style.css").getInputStream());
//            CssFile cssFile = helper.getCSS(new FileInputStream(scss));
        cssResolver.addCss(cssFile);

        // HTML, 폰트 설정
        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontProvider.register(sfont, "abc"); // MalgunGothic은

        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);

        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new AbstractImageProvider() {
            @Override
            public String getImageRootPath() {
                try{
                    return new ClassPathResource("templates/commission").getURL().getPath();
                }catch (Exception e){
                    e.printStackTrace();

                    return null;
                }
            }
        });

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        XMLWorker worker = new XMLWorker(css, true);
        XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));

        // 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
//            String sHtml = "<html><head></head><body style='font-family:MalgunGothic;'>" + map.get("printData").toString() + "</body></html>";
        // byte[] bHtml = (map.get("printData").toString()).getBytes();

        xmlParser.parse(new StringReader(process));
        document.close();
        writer.close();
    }catch (Exception e){
        e.printStackTrace();

    }

        return null;

    }
}
