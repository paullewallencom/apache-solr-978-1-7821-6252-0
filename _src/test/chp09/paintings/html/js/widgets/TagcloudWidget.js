(function (callback) {
  if (typeof define === 'function' && define.amd) {
    define(['core/AbstractFacetWidget'], callback);
  }
  else {
    callback();
  }
}(function () {

(function ($) {

AjaxSolr.TagcloudWidget = AjaxSolr.AbstractFacetWidget.extend({
  afterRequest: function () {
    if (this.manager.response.facet_counts.facet_fields[this.field] === undefined) {
      $(this.target).html('no items found in current selection');
      return;
    }

    var maxCount = 0;
    var objectedItems = [];
    for (var facet in this.manager.response.facet_counts.facet_fields[this.field]) {
      var count = parseInt(this.manager.response.facet_counts.facet_fields[this.field][facet]);
      if (count > maxCount) {
        maxCount = count;
      }
      objectedItems.push({ facet: facet, count: count });
    }
    objectedItems.sort(function (a, b) {
      return a.facet < b.facet ? -1 : 1;
    });

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
    
    
    $(this.target).empty();
    for (var i = 0, l = objectedItems.length; i < l; i++) {
      var facet = objectedItems[i].facet;
      var facet_text = normalize_text(facet);
      
      if(facet){
    	  var tag_size = parseInt(objectedItems[i].count / maxCount * 2);
          
          $(this.target).append(
            $('<a href="#" class="tagcloud_item"></a>')
            .text(facet_text+' ['+objectedItems[i].count+']')
            .addClass('tagcloud_size_' + tag_size)
            .click(this.clickHandler(facet))
          );
      }
      
    }
  }
});

})(jQuery);

}));
