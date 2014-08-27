(defproject clj-hdf5-test "0.1.0-SNAPSHOT"
  :description "There is no spoon."
  :url "http://github.com/hokkaido/clj-hdf5-test"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [net.mikera/core.matrix "0.28.0"]
                 [io.kimchi/cisd-jhdf5-core "13.06.2"]
                 [io.kimchi/cisd-jhdf5-native-deps "13.06.2"]]
  :main ^:skip-aot clj-hdf5-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
