var Manager;

require.config({
	paths : {
		core : '../ajax-solr/core',
		managers : '../ajax-solr/managers',
		widgets : '../ajax-solr/widgets',
		paintings : 'widgets'
	},
	urlArgs : "bust=" + (new Date()).getTime() * (new Date()).getTime()
});

(function($) {

	define([ 'managers/Manager.jquery', 'core/ParameterStore',
			'paintings/ResultWidget', 'paintings/TagcloudWidget',
			'paintings/CurrentSearchWidget', 'paintings/AutocompleteWidget',
			'widgets/jquery/PagerWidget' ], function() {
		$(function() {
			Manager = new AjaxSolr.Manager({
				solrUrl : 'http://localhost:8983/solr/arts/'
			});
			Manager.addWidget(new AjaxSolr.ResultWidget({
				id : 'result',
				target : '#docs'
			}));
			Manager.addWidget(new AjaxSolr.PagerWidget({
				id : 'pager',
				target : '#pager',
				prevLabel : '&lt;',
				nextLabel : '&gt;',
				innerWindow : 1,
				renderHeader : function(perPage, offset, total) {
					$('#pager-header').html(
							$('<span></span>').text(
									'displaying ' + Math.min(total, offset + 1)
											+ ' to '
											+ Math.min(total, offset + perPage)
											+ ' of ' + total));
				}
			}));
			var fields = [ 'museum_entity', 'artist_entity', 'city_entity' ];
			for ( var i = 0, l = fields.length; i < l; i++) {
				Manager.addWidget(new AjaxSolr.TagcloudWidget({
					id : fields[i],
					target : '#' + fields[i],
					field : fields[i]
				}));
			}
			Manager.addWidget(new AjaxSolr.CurrentSearchWidget({
				id : 'currentsearch',
				target : '#selection'
			}));
			Manager.addWidget(new AjaxSolr.AutocompleteWidget({
				id : 'text',
				target : '#search',
				fields : [ 'museum_entity', 'artist_entity', 'city_entity' ]
			}));
			Manager.init();
			Manager.store.addByValue('q', '*:*');
			var params = {
				facet : true,
				'facet.field' : [ 'museum_entity', 'artist_entity',	'city_entity' ],
				'facet.limit' : 20,
				'facet.mincount' : 1,
				'f.city_entity.facet.limit' : 10,
				'json.nl' : 'map',
				'fq' : 'artist_entity:caravaggio'
			};
			for ( var name in params) {
				Manager.store.addByValue(name, params[name]);
			}
			Manager.doRequest();
		});

		$.fn.showIf = function(condition) {
			if (condition) {
				return this.show();
			} else {
				return this.hide();
			}
		}
	});

})(jQuery);
