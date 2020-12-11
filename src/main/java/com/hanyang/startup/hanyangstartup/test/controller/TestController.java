package com.hanyang.startup.hanyangstartup.test.controller;

import com.hanyang.startup.hanyangstartup.common.util.EncodingUtil;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
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
import com.itextpdf.tool.xml.pipeline.end.ElementHandlerPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.xml.DomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.itextpdf.text.Document;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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

    @GetMapping
    public byte[] imageJoinTest (HttpServletRequest req, HttpServletResponse res) throws FileNotFoundException, IOException, DocumentException {
        try{

            LocalDateTime localDateTime = LocalDateTime.now();
            int year = localDateTime.getYear();
            int month = localDateTime.getMonthValue();
            int day = localDateTime.getDayOfMonth();
            String name = "에네이";

            Map<String,Object> map = new HashMap();
            map.put("year",year);
            map.put("month",month);
            map.put("day",day);
            map.put("name",name);

            String fileName = name+"_위촉장.pdf";

            fileName = encodingUtil.browserFileNameEncoding(fileName, encodingUtil.getBrowser(req));

            Context context = new Context();
            context.setVariable("info", map);

            String process = templateEngine.process("commission/commission", context);

            String scss = new ClassPathResource("templates/commission/style.css").getURL().getPath();
            String sfont = new ClassPathResource("templates/commission/abc.ttf").getURL().getPath();

            Document document = new Document(PageSize.A4, 0, 0, 00, 00);

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
            CssFile cssFile = helper.getCSS(new FileInputStream(scss));
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