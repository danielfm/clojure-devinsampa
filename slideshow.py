#-*- coding:utf-8 -*-

"""
Slideshow settings file.
"""

from pydozeoff.conf.slide import *

from textile import textile


# Presentation info
TITLE    = u"Programação Funcional e Concorrente para JVM com Clojure"
SPEAKER  = u"Daniel Martins"
COMPANY  = u"Destaquenet Soluções em Tecnologia"
LOCATION = u"Dev in Sampa 2010"
DATE     = u"2010-08-14"

# Extra info
COMPANY_WEBSITE = u"destaquenet.com"
SPEAKER_TWITTER = u"danielfmt"
SPEAKER_EMAIL   = u"daniel@%s" % COMPANY_WEBSITE
EMAIL_SUBJECT   = u"[%s] %s" % (LOCATION, TITLE)

# Slideshow config
THEME = "simple_white"
DEFAULT_VIEW = "slideshow"

TEMPLATE_ENGINE_FILTERS = {
    "textile": lambda content: textile(content),
}

# Presentation slides
BASE   = "slides/base.html"
SLIDES = slides(
    simple ("capa.html"),

    section("intro",
        image  ("capa.html"),
        simple ("audiencia.html"),
        bullets("agenda.html"),
        code   ("helloworld.html"),
        simple ("tipos.html"),
        bullets("estruturas.html"),
        simple ("dados_funcoes.html"),
        code   ("dados_funcoes_ex.html"),
        simple ("jvm.html"),
        code   ("homo.html"),
        code   ("interop.html"),
    ),

    section("funcional",
        simple("capa.html"),
        simple("mandamentos.html"),
        simple("mandamento_1.html"),
        code  ("mandamento_1_ex_1.html"),
        code  ("mandamento_1_ex_2.html"),
        simple("mandamento_2.html"),
        code  ("mandamento_2_ex.html"),
        simple("mandamento_3.html"),
        code  ("mandamento_3_ex.html"),
        simple("euler.html"),
        code  ("euler_solucao.html"),
    ),

    section("concorrencia",
        simple("mundo_real.html"),
        simple("mutabilidade.html"),

        section("ex_1",
            simple("capa.html"),
            code  ("gerador_errado.html"),
            image ("../ra.html"),
            code  ("bytecode.html"),
            simple("locks.html"),
            code  ("synchronized.html"),
            simple("identidade_estado.html"),
            simple("refs.html"),
            simple("ref_solucao.html"),
            image ("solucao_1.html"),
            image ("solucao_2.html"),
        ),

        section("ex_2",
            simple("capa.html"),
            code  ("transferencia_errado_1.html"),
            simple("transferencia_oops.html"),
            code  ("transferencia_errado_2.html"),
            image ("../ra.html"),
            code  ("deadlock.html"),
            code  ("transferencia_ok.html", extra_classes="smallcode"),
            image ("parei.html"),
            simple("stm.html"),
            code  ("solucao.html"),
            image ("ref_workflow.html"),
            code  ("transacao.html", extra_classes="simple"),
        ),

        section("ex_3",
            simple("capa.html"),
            code  ("thread.html"),
            image ("limitacoes.html"),
            simple("api.html"),
            simple("agent.html"),
            code  ("solucao.html"),
            simple("erros.html"),
            code  ("erros_ex.html"),
        ),

        simple("refs_recursos.html"),
    ),

    section("conclusao",
        bullets("faltou.html"),
        image  ("clojure.html"),
        image  ("oci.html"),
        image  ("pragmatic.html"),
        image  ("manning.html"),
    ),

    simple ("fim.html"),
)
