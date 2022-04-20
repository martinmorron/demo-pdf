package com.example.demopdf

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


@RestController
class PDFGeneratorController(val templateEngine: TemplateEngine) {

    @GetMapping("/generate")
    fun generate(model: Model): String {
        model["title"] = "Title"


        val context = Context()
        context.setVariable("title", "NEW REPORT");

        val stringTemplate = templateEngine.process("report.html", context)

        val renderer = PdfRendererBuilder()
            .useFastMode()
            .withHtmlContent(stringTemplate, null)

        val stream = ByteArrayOutputStream()
        renderer.toStream(stream);
        renderer.run()

        FileOutputStream("final_report.pdf").use { outputStream -> stream.writeTo(outputStream) }

        return "Report completed"
    }
}
