#-------------------------------------------------------------------------------
# Copyright 2013 Alfredo Serafini (http://seralf.it)
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#-------------------------------------------------------------------------------

The pdfs example is constructed step-by-step in the Chapter 2.
Here you will find:
* The full, final configuration in the /pdfs_step-by-step/pdfs/ folder. This contains all the step-by-step ones, with the addition of a SimpleTextCodec to inspect the index
* The first step /pdfs_step-by-step/pdfs_1/, containing only the schemaless definition.
* The second step /pdfs_step-by-step/pdfs_2/, containing tokenization and lowercase filtering.
* The third step /pdfs_step-by-step/pdf_3/, introducing metadata extraction with Tika
* The fourth step /pdfs_step-by-step/pdfs_4/, generating uid hash and suggesting deduplication

The libraries imported are found under:
SOLR_DIST/contrib/extraction/lib

from: SOLR_DIST/dist/: solr-cell-4,5,0.jar
from: SOLR_DIST/extraction/lib/: tika-core-1.4.jar, tika-parsers-1.4.jar, pdfbox-1.8.1.jar, fontbox-1.8.1.jar, xercesimpl-2.9.2.jar
