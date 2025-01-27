(function (callback) {
  if (typeof define === 'function' && define.amd) {
    define(['core/AbstractTextWidget'], callback);
  }
  else {
    callback();
  }
}(function () {

(function ($) {

AjaxSolr.AutocompleteWidget = AjaxSolr.AbstractTextWidget.extend({
  afterRequest: function () {
    $(this.target).find('input').unbind().removeData('events').val('');

    var self = this;

    var callback = function (response) {
      var list = [];
      for (var i = 0; i < self.fields.length; i++) {
        var field = self.fields[i];
        for (var facet in response.facet_counts.facet_fields[field]) {
//        	var label = $('<strong>OPS</strong>');
        list.push({
            field: field,
            value: facet,
            label: normalize_text(facet) + '\n[' + response.facet_counts.facet_fields[field][facet] + ' in ' + field+']'
          });
        }
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
      
      
      self.requestSent = false;
      $(self.target).find('input').autocomplete('destroy').autocomplete({
        source: list,
        select: function(event, ui) {
          if (ui.item) {
            self.requestSent = true;
            if (self.manager.store.addByValue('fq', ui.item.field + ':' + AjaxSolr.Parameter.escapeValue(ui.item.value))) {
              self.doRequest();
            }
          }
        }
      });

      // This has lower priority so that requestSent is set.
      $(self.target).find('input').bind('keydown', function(e) {
        if (self.requestSent === false && e.which == 13) {
          var value = $(this).val();
          if (value && self.set(value)) {
            self.doRequest();
          }
        }
      });
    } // end callback

    var params = [ 'rows=0&facet=true&facet.limit=-1&facet.mincount=1&json.nl=map' ];
    for (var i = 0; i < this.fields.length; i++) {
      params.push('facet.field=' + this.fields[i]);
    }
    var values = this.manager.store.values('fq');
    for (var i = 0; i < values.length; i++) {
      params.push('fq=' + encodeURIComponent(values[i]));
    }
    params.push('q=' + this.manager.store.get('q').val());
    $.getJSON(this.manager.solrUrl + 'select?' + params.join('&') + '&wt=json&json.wrf=?', {}, callback);
  }
});

})(jQuery);

}));
