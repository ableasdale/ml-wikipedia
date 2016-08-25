
Tested with the following:
* **enwiki-20160720-pages-articles-multistream.xml** (~54GB) file, which should create 16,753,779 individual **page** elements.
* **enwiki-20160820-pages-articles-multistream.xml** (~55GB) file, which should create 16,824,194 individual **page** elements.

## Setup

### Get MLCP 
####(latest release can be found at https://developer.marklogic.com/products/mlcp)

```bash
wget https://developer.marklogic.com/download/binaries/mlcp/mlcp-8.0-5-bin.zip
unzip mlcp-8.0-5-bin.zip
```

### Getting the current Wikipedia dump data

```bash
wget http://dumps.wikimedia.your.org/enwiki/20160820/enwiki-20160820-pages-articles-multistream.xml.bz2
bzip2 -d enwiki-20160820-pages-articles-multistream.xml.bz2
```

### TODO DBPedia ?
```
http://web.informatik.uni-mannheim.de/DBpediaAsTables/DBpediaAsTablesJSON.tar
http://downloads.dbpedia.org/2016-04/core-i18n/en/
```

### Creating the Wikipedia Database

```xquery
xquery version "1.0-ml"; 

import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
import module namespace info = "http://marklogic.com/appservices/infostudio" at "/MarkLogic/appservices/infostudio/info.xqy";

declare variable $DATABASE as xs:string := "Wikipedia";

let $_ := info:database-create($DATABASE, 2, (), "/space")
return

admin:save-configuration(
  admin:database-add-range-element-index( 
    admin:get-configuration(), 
    xdmp:database($DATABASE), 
    (
      admin:database-range-element-index("string", "http://www.mediawiki.org/xml/export-0.10/", "title", "http://marklogic.com/collation/codepoint", fn:false(), "ignore"),
      admin:database-range-element-index("dateTime", "http://www.mediawiki.org/xml/export-0.10/", "timestamp", (), fn:false(), "ignore")
    )
  )
)
```

### Loading with mlcp

Execute **load-wikipedia-xml.sh**

A sample options file (**options.txt**):

```bash
-input_file_path
enwiki-20160720-pages-articles-multistream.xml
-host
{HOSTNAME}
-username
*****
-password
*****
-database
Wikipedia
-xml_repair_level
full 
-input_file_type
aggregates
-aggregate_record_element
page
-aggregate_record_namespace
http://www.mediawiki.org/xml/export-0.10/
-fastload 
-batch_size
50
-transaction_size
1
-thread_count
48
```


## Notes from earlier runs

### Initial test load (to local machine - not optimal)
```bash
mlcp-8.0-5/bin/mlcp.sh IMPORT -input_file_path="enwiki-20160720-pages-articles-multistream.xml" \
-host="localhost" -username="*****" -password="*****" -database="Wikipedia2" -xml_repair_level="full" \
-input_file_type="aggregates" -aggregate_record_element="page" -transaction_size="100" -thread_count="16" \
-aggregate_record_namespace="http://www.mediawiki.org/xml/export-0.10/"
```

### Fastload test (loading over network)
```bash
mlcp-8.0-5/bin/mlcp.sh IMPORT -input_file_path="enwiki-20160720-pages-articles-multistream.xml" \
-host="{host}" -username="*****" -password="*****" -database="Wikipedia" \
-xml_repair_level="full" -input_file_type="aggregates" -aggregate_record_element="page" \
-aggregate_record_namespace="http://www.mediawiki.org/xml/export-0.10/" -fastload -batch_size="20" -transaction_size="1" \
-thread_count="32"
```


### MLCP output on completion
```bash
16/08/25 04:19:23 INFO contentpump.LocalJobRunner: com.marklogic.mapreduce.ContentPumpStats:
16/08/25 04:19:23 INFO contentpump.LocalJobRunner: INPUT_RECORDS: 16824194
16/08/25 04:19:23 INFO contentpump.LocalJobRunner: OUTPUT_RECORDS: 16824194
16/08/25 04:19:23 INFO contentpump.LocalJobRunner: OUTPUT_RECORDS_COMMITTED: 16824194
16/08/25 04:19:23 INFO contentpump.LocalJobRunner: OUTPUT_RECORDS_FAILED: 0
16/08/25 04:19:23 INFO contentpump.LocalJobRunner: Total execution time: 13987 sec
```