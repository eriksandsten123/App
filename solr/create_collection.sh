# Kopiera managed-schema och solrconfig.xml i /Users/erik/Apps/solr-7.3.1/server/solr/configsets/_default/conf
./solr create_collection -c app_collection -d /Users/erik/IdeaProjects/app/solr -s 1 -rf 1 -p 8983
./solr delete -c app_collection