Ext.define('Luntan.view.courses.CourseInstanceTaskController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.courseinstancetasklist',

    onSelectionChange: function(sm, rec) {},

    onCreate: function()
    {
        var grid = this.getView(),
			thisEDoc = this.getViewModel().get('current.edoc.id');
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.CourseInstance');

        r.set('economyDocId',thisEDoc);
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);

    },

   	onBeforeRender: function (grid) {

   	},


   	onCourseInstances: function () {
		var year = this.getViewModel().get('current.edoc.year');
		window.open(Luntan.data.Constants.BASE_URL.concat('public/view/courseinstanceinfo?year=').concat(year == null ? new Date().getFullYear() : year));
   	},


   	init: function (view) {

		var tb = view.down('toolbar');
						

		tb.remove(view.lookupReference('btnCreate'));
		tb.remove(view.lookupReference('btnRemove'));

		tb.insert(2,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Se årets kurser och kursledare',
				text: 'Kurser och kursledare',
				reference: 'btnCourseInstances',
				disabled: false,
				listeners: {
				  click: 'onCourseInstances'
				}
			});			

		view.findPlugin('rowediting').addListener('beforeEdit', function(rowEditing, context) {
			/* Disabling editing of specific fileds */
			var form   = rowEditing.getEditor().form,
				fields  = [
					form.findField('IBG'),
					form.findField('ICM'),
					form.findField('IEG'),
					form.findField('IOB')
				],
				status = context.grid.getViewModel().get('current.edoc.locked');
			if(status){
				fields.forEach(function(field){
					field.disable()
				});


// 				fields.forEach(
// 					(field) => {field.disable()}
// 				);


			} else {
				fields.forEach(function(field){
					field.enable()
				});
// 				fields.forEach(
// 					(field) => {field.enable()}
// 				);

			}
			return true;
			/* Disabling editing of entire record */
//			return !context.grid.getViewModel().get('current.edoc.locked');
        });

	}

});
