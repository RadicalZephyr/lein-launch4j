# lein-launch4j

A Leiningen plugin to use [launch4j][l4j-home] to generate an .exe wrapper for
your Clojure project.

## Usage

Install [launch4j][l4j-home] somewhere on your local machine.

[l4j-home]: http://launch4j.sourceforge.net/

Put `[lein-launch4j "0.1.0-SNAPSHOT"]` into the `:plugins` vector of
your `:user` profile.

Then add `:launch4j-install-dir
"/path/to/launch4j"` to your `:user` profile.  It should end up
looking something like this:

    {:user
     {:plugins [[lein-launch4j "0.1.0"]]
      :launch4j-install-dir "/home/geoff/launch4j"}}

Now you need to create a [config.xml][l4j-docs] file for your project.
Place it somewhere in your project, I happen to like the `resources`
folder.  Then put `:launch4j-config-file "relative-path/to/config.xml"`

[l4j-docs]: http://launch4j.sourceforge.net/docs.html

Now you can package your app with launch4j just by running:

    $ lein launch4j


Leiningen 1.x is not currently supported.

## License

Copyright © 2013 Geoff Shannon

Distributed under the Eclipse Public License, the same as Clojure.
