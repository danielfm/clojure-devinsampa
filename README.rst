Programação Funcional e Concorrente para JVM com Clojure
========================================================

Palestra apresentada no Dev in Sampa 2010.

Os slides da apresentação podem ser `vistos aqui`_, ou baixados a partir da
`página de downloads`_ do projeto.

"Compilando" a apresentação
---------------------------

Requisitos
``````````

* GNU/Linux
* `Python`_ 2.5+
* `PIP`_ 0.8+

Instalando as dependências
``````````````````````````

Após instalados os requisitos, abra um terminal no diretório-raíz do projeto
e rode o comando ``bin/install-deps.sh`` para baixar o restante dos módulos
necessários para gerar os arquivos da apresentação.

Servindo a apresentação
```````````````````````

Feito isso, basta rodar o comando ``bin/offline.sh`` para gerar uma versão
offline da apresentação, ou ``bin/run-server.sh`` para servir a apresentação
através de um servidor web local (porta ``8080``).

Explorando os códigos de exemplo
--------------------------------

Java
````

Os exemplos de código Java estão em ``src/java``, cujos arquivos estão
distribuídos no pacote ``br.com.devinsampa.concorrencia.*``. Basta utilizar
``javac`` ou ``java`` para compilar ou rodar um exemplo específico.

Clojure
```````

Os exemplos de código Clojure estão em ``src/clojure``. Basta utilizar o
comando ``bin/clojure.sh <arquivo.clj>`` para rodar um exemplo específico.

Para iniciar o REPL, ambiente de execução interativo da linguagem, basta rodar
o comando ``bin/clojure.sh`` sem argumentos.

.. _vistos aqui: http://danielfm.github.com/clojure-devinsampa/
.. _página de downloads: http://github.com/danielfm/clojure-devinsampa/downloads
.. _Python: http://python.org
.. _PIP: http://pip.openplans.org
