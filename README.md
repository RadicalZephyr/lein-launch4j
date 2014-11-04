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

## Valid `project.clj` Options

I've tried to provide sensible defaults for all of the required
`launch4j` options, and the default executable name is derived from
the name and version of the project as `<name>-<version>.exe`.

But there are many different ways that `launch4j` can be
configured. Right now the only documentation in this project exists as
comments on the `default-options` var in the `leiningen.launch4j.xml`
namespace. The two that aren't documented there are the `:exe-name`
option which goes in the root of your project definition. It is used
to override the default file name for the final executable. The second
is the `:launch4j` key, where you place the arbitrary options that are
transformed into an XML configuration for launch4j. It should be a
dictionary with keywords named for the XML tags. When a key has a map
as it's value, these tags are nested inside the key tag.

## License

Copyright © 2013-2014 Geoff Shannon

Distributed under the Eclipse Public License, the same as Clojure.
