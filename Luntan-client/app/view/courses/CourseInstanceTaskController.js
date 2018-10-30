Ext.define('Luntan.view.courses.CourseInstanceTaskController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.courseinstancetasklist',

    
    onCreate: function()
    {
        var grid = this.getView(),
			thisEDoc = this.getViewModel().get('current.edocId');
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.CourseInstance');

        r.set('economyDocId',thisEDoc);
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	}

    
});
