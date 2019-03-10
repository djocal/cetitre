package cetitre

import groovy.transform.CompileStatic
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@CompileStatic
@Controller("/bonjour")
class BonjourController {

    @Get(uri = "/", produces = MediaType.TEXT_PLAIN)
    String index() {
        return "Bonjour !"
    }
}
