(function (callback) {
  if (typeof define === 'function' && define.amd) {
    define(['core/AbstractWidget'], callback);
  }
  else {
    callback();
  }
}(function () {

(function ($) {

AjaxSolr.ResultWidget = AjaxSolr.AbstractWidget.extend({
  start: 0,

  beforeRequest: function () {
    $(this.target).html($('<img>').attr('src', 'images/ajax-loader.gif'));
  },

  facetLinks: function (facet_field, facet_values) {
    var links = [];
    if (facet_values) {
      for (var i = 0, l = facet_values.length; i < l; i++) {
        if (facet_values[i] !== undefined) {
          links.push(
            $('<a href="#"></a>')
            .text(facet_values[i])
            .click(this.facetHandler(facet_field, facet_values[i]))
          );
        }
        else {
          links.push('no items found in current selection');
        }
      }
    }
    return links;
  },

  facetHandler: function (facet_field, facet_value) {
    var self = this;
    return function () {
      self.manager.store.remove('fq');
      self.manager.store.addByValue('fq', facet_field + ':' + AjaxSolr.Parameter.escapeValue(facet_value));
      self.doRequest();
      return false;
    };
  },

  afterRequest: function () {
    $(this.target).empty();
    for (var i = 0, l = this.manager.response.response.docs.length; i < l; i++) {
      var doc = this.manager.response.response.docs[i];
      $(this.target).append(this.template(doc));

      var items = [];
      items = items.concat(this.facetLinks('museum_entity', doc.museum));
      items = items.concat(this.facetLinks('artist_entity', doc.artist));
      items = items.concat(this.facetLinks('city_entity', doc.artist));

      var $links = $('#links_' + doc.id);
      $links.empty();
      for (var j = 0, m = items.length; j < m; j++) {
        $links.append($('<li></li>').append(items[j]));
      }
    }
  },

  template: function (doc) {
  
  	if(!doc.label) return;
  
    var snippet = '';
    // var text = doc.fullText[0] // TODO
    var text = doc.comment
    if (text!=undefined && text.length > 200) {
      snippet += text.substring(0, 300);
      snippet += '<span style="display:none;">' + text.substring(300);
      snippet += '</span> <a href="#" class="more">more</a>';
    }
    else {
      snippet += text;
    }

    var output = '<article class="painting">';
    
    var wikipedia_link;
    if(doc.isPrimaryTopicOf)    
    	wikipedia_link = '<a href="'+doc.isPrimaryTopicOf+'" class="wikipedia_link"><img src="images/wikipedia_32.png" /></a>';
    
    output += '<header><a href="'+doc.uri+'"><h2>'+doc.label+'</h2></a></header>'
        
    function fix_img(url){
    	return url.replace(/commons/, 'en');
    }

    function imgExists(url, output) {
        var img = new Image();
        img.onerror = function() {
        	checkImage(false, url, output);
        }
        img.onload = function () {
        	checkImage(true, url, output);
        }
        img.src = url;
        console.log("IMG? " + img.src);
        return img.src;
    }

    function checkImage(exists, src, output) {
        if(!exists) 
        	src = src.replace(/commons/, 'en')
//        console.log("image exists? " + src + " = " + !exists); // Usage example.
        output += '<img src="' + src + '" class="thumb" />';
        console.log("\n\n_____________")
        console.log(output)
        return output;
    }
    
    // HACK for the resource
    if (doc.depiction){
    
// doc.depiction[0].replace(/commons/, 'en')
    	
// TODO: var img = imgExists(doc.depiction[0], output);
    	var img = doc.depiction[0]
    	
    	output += '<img src="' + img + '" class="thumb" />';
    }
    
    output += '<p>' + snippet + '</p>';
    
	output += wikipedia_link    
	
	if(doc.sameAs){
	var sameAs = '<footer class="sameAs">'
		sameAs += '<b>same as</b><ul>'
    	for(i=0;i<doc.sameAs.length;i++){
		    sameAs += '<li><a href="'+doc.sameAs[i]+'" class="links">'+doc.sameAs[i]+'</a></li>'
    	}
    	sameAs += '<ul/></footer>'
    }
    
    output += sameAs
    
	output += '<p id="links_' + doc.uri + '" class="links"></p>';
    output += '</article>';
    
    return output;
  },

  init: function () {
    $(document).on('click', 'a.more', function () {
      var $this = $(this),
          span = $this.parent().find('span');

      if (span.is(':visible')) {
        span.hide();
        $this.text('more');
      }
      else {
        span.show();
        $this.text('less');
      }

      return false;
    });
  }
});

})(jQuery);

}));
