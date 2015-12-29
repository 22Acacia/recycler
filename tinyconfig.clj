{:cluster   {:name "recycler" :initial_node_count 3 :master_auth {:username "hx" :password "hstack"}}
 :opts      {:composer-classpath    ["/usr/local/lib/angleddream-bundled-0.1-ALPHA.jar"] ;where all the jar files live. no trailing slash. may be overriden by env var in production? also be sure to build thick jars from angled-dream for deps
             :maxNumWorkers "1" :numWorkers "1" :zone "europe-west1-c" :workerMachineType "n1-standard-1"
             :stagingLocation "gs://recycler-test/recycler"}
 :provider  {:credentials "${file(\"/home/ubuntu/demo-config/account.json\")}"  :project "hx-test"}
 :pipelines {"recycler"
             {:transform-graph ["/usr/local/lib/recycler-0.1-ALPHA.jar"]}
             }
 :sources   {"rec" {:type "kub"}
             }
 :sinks     {
             "recyclersink" {:type "gcs" :bucket "recycler"}
             }
 :edges     [{:origin "rec" :targets ["recycler"]}
             {:origin "recycler" :targets ["recyclersink"]}
             ]}


