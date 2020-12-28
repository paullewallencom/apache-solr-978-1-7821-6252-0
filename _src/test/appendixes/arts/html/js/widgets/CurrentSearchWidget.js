(function (callback) {
  if (typeof define === 'function' && define.amd) {
    define(['core/AbstractWidget'], callback);
  }
  else {
    callback();
  }
}(function () {

(function ($) {

AjaxSolr.CurrentSearchWidget = AjaxSolr.AbstractWidget.extend({
  start: 0,

  afterRequest: function () {
    var self = this;
    var links = [];

    var q = this.manager.store.get('q').val();
    
    if (q != '*:*') {
      links.push($('<a href="#"></a>').text('(x) ' + q).click(function () {
        self.manager.store.get('q').val('*:*');
        self.doRequest();
        return false;
      }));
    }
    
    function normalize_text(text){
    	return text
      	.replace(/.*\/(.*?)/, '$1') // fix names
		.replace(/_+/, ' ')
		.replace(/_+/, ' ')
		.replace(/_+/, ' ')
		.replace(/%e2%80%93/, '-')
		.replace(/%c3%89/, 'É')
		.replace(/%c3%a9/, 'é')
		.replace(/%C3%A9/, 'é')
		.replace(/%c3%a8/, 'è')
		.replace(/%c3%ad/, 'í')
    	.replace(/%c3%b6/, 'ö')
    	.replace(/%c3%bc/, 'ü');
    }

    var fq = this.manager.store.values('fq');
    for (var i = 0, l = fq.length; i < l; i++) {
    	var qtext = fq[i].replace(/.*\/(.*?)"/, '$1') // fix names
	    	.replace(/_+/, ' ')
	    	.replace(/_+/, ' ')
	    	.replace(/_+/, ' ')
	    	.replace(/%C3%A9/, 'é');
      links.push($('<a href="#"></a>').text('(X) ' + normalize_text(qtext)).click(self.removeFacet(fq[i])));
    }

    if (links.length > 1) {
      links.unshift($('<a href="#"></a>').text('remove all').click(function () {
        self.manager.store.get('q').val('*:*');
        self.manager.store.remove('fq');
        self.doRequest();
        return false;
      }));
    }

    if (links.length) {
      var $target = $(this.target);
      $target.empty();
      for (var i = 0, l = links.length; i < l; i++) {
        $target.append($('<li></li>').append(links[i]));
      }
    }
    else {
      $(this.target).html('<li>Viewing all documents!</li>');
    }
  },

  removeFacet: function (facet) {
    var self = this;
    return function () {
      if (self.manager.store.removeByValue('fq', facet)) {
        self.doRequest();
      }
      return false;
    };
  }
});

})(jQuery);

}));
