Ext.define('Luntan.view.courses.CourseInstanceController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.courseinstancelist',

    onSelectionChange: function(sm, rec) {},

    onCreate: function()
    {
        var grid = this.getView(),
			thisEDoc = this.getViewModel().get('current.edoc.id');
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.CourseInstance');

        r.set('economyDocId',thisEDoc);
        r.set('modelCase','DEFAULT');
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);

    },

   	onBeforeRender: function (grid) {
   	},

   	init: function (view) {

       view.findPlugin('rowediting').addListener('beforeEdit', function(rowEditing, context) {
			/* Disabling editing of specific fileds */
			var form   = rowEditing.getEditor().form,
				fields  = [
					form.findField('courseId'),
					form.findField('extraDesignation'),
					form.findField('firstInstance'),
					form.findField('startRegStudents'),
					form.findField('fundingModelId')
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
					field.disable()
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
