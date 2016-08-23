Initial test load (not optimal)
```bash
mlcp-8.0-5/bin/mlcp.sh IMPORT -input_file_path="enwiki-20160720-pages-articles-multistream.xml" \
-host="localhost" -username="*****" -password="*****" -database="Wikipedia2" -xml_repair_level="full" \
-input_file_type="aggregates" -aggregate_record_element="page" -transaction_size="100" -thread_count="16" \
-aggregate_record_namespace="http://www.mediawiki.org/xml/export-0.10/"
```
