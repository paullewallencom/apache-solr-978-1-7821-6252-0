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


This folder contains the resources used in the examples. Some of the listed folders are actually zipped to reduce transfer times: you can easily unzip them with your favourite tool, or simply call the provided scripts extract.sh (*NIX) or extract.bat (win), in order to generate them.


/pdfs
contains some example PDF useful for testing the SolrCell/Tika components. You can also add here your own PDF files for your experimentations.

/dbpedia_artists
/dbpedia_paintings
/dbpedia_museums
/dbpedia_subjects
Those directories contains data downloaded from dbpedia, if you don't want to download them by yourself. Every one contains a /downloaded subfolder for the downloaded files, and a /solr_docs subfolder containing the corresponding Solr XML document.

/wgarts
contains data downloaded from the Web Gallery of Arts site, and provided both as a single CSV file and an SQLite database file.
