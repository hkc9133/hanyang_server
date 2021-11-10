package com.hanyang.startup.hanyangstartup.test.controller;

import com.hanyang.startup.hanyangstartup.common.util.EncodingUtil;
import com.hanyang.startup.hanyangstartup.mentoring.domain.Mentor;
import com.hanyang.startup.hanyangstartup.mentoring.service.MentoringService;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.itextpdf.text.Document;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private EncodingUtil encodingUtil;

    @Autowired
    private MentoringService mentoringService;

    @PostMapping("/aaa")
    public void abcd(HttpServletRequest req,HttpServletResponse res) throws IOException{

//        RedirectView redirectView = new RedirectView("http://localhost:3000/");
//        redirectView.setExposeModelAttributes(false);
//        System.out.println("리다이렉트");
//        System.out.println(req.getRequestURL());
//        System.out.println(redirectView);
//        System.out.println("리다이렉트");
//        res.sendRedirect("http://localhost:3000/");
//        return redirectView;

    }

    @GetMapping("/sns_result")
    public void redirectWithUsingRedirectView(HttpServletRequest req,HttpServletResponse res) throws IOException{

//        RedirectView redirectView = new RedirectView("http://localhost:3000/");
//        redirectView.setExposeModelAttributes(false);
//        System.out.println("리다이렉트");
//        System.out.println(req.getRequestURL());
//        System.out.println(redirectView);
//        System.out.println("리다이렉트");
        res.sendRedirect("http://localhost:3000/");
//        return redirectView;
    }

    @GetMapping
    public byte[] imageJoinTest (HttpServletRequest req, HttpServletResponse res, Principal principal) throws FileNotFoundException, IOException, DocumentException {
        try{
            Mentor mentor = new Mentor();
            mentor.setUserId("admin1");

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

//
//
//    @GetMapping("/login")
//    public void test(Principal principal, HttpServletRequest req, HttpServletResponse res){
//        OAuthClient oAuthClient = new OAuthClient();
//        //client id 정보 입력
//        oAuthClient.setClientId("5b67a3b231677f88cfcaba41b77d872");
//        //client scret 정보 입력
//        oAuthClient.setClientSecret("8483b416bbb356f9c6379f5dbcd8c80");
//        //앱에서 사용하는 API의 ID들 (예: 1,2,3)
//        oAuthClient.setScope("1862,1864,1865,1863,1867,1866");
//        //redirect uri 정보 입력
//        oAuthClient.setRedirectUrl("http://127.0.0.1/auth/login");
//    }
}
