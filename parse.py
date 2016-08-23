import xml.parsers.expat
import sys

FILE = "/Users/ableasdale/Downloads/enwiki-20160720-pages-articles-multistream.xml" 


# reload(sys)
# sys.setdefaultencoding('utf-8')
print(sys.getdefaultencoding())
# FILE = unicode(FILE, 'utf-8')

parser = xml.parsers.expat.ParserCreate()
parser.ParseFile(open(FILE, 'r'))
