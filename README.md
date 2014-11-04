# lein-launch4j

A Leiningen plugin to use [launch4j][l4j-home] to generate an .exe wrapper for
your Clojure project.

[l4j-home]: http://launch4j.sourceforge.net/


## Usage

Put `[lein-launch4j "0.2.0"]` into the `:plugins` vector of the
`project.clj` you want to be able to wrap.

Now you need to create a [config.xml][l4j-docs] file for your project.
Place it somewhere in your project, I happen to like the `resources`
folder.  Then put `:launch4j-config-file "relative-path/to/config.xml"`

[l4j-docs]: http://launch4j.sourceforge.net/docs.html

Now you can package your app with launch4j just by running:

    $ lein launch4j


## License

Copyright © 2013-2014 Geoff Shannon

Distributed under the Eclipse Public License, the same as Clojure.
