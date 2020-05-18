Ext.define('Luntan.view.courses.ExaminerListingController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.examinerslisting',

    
    onCreate: function()
    {
        var grid = this.getView(), 
        	thisCourseId = this.getViewModel().get('current.cid'),
        	theStore = grid.getStore(),
			itemNum = theStore.data.items.length;
			
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.ExaminersList');
		r.set('decided', true);
		r.set('defaultExaminers', []);
		
		
		var rec = theStore.insert(itemNum, r);
//		grid.ownerGrid.refreshRank();
        grid.plugins[0].startEdit(rec[0]);
        
    },

   onRemove: function()
    {
        var grid = this.getView();
        var sm = grid.getSelectionModel();
            grid.plugins[0].cancelEdit();
            grid.getStore().remove(sm.getSelection());
            if (grid.getStore().getCount() > 0) {
                sm.select(0);
            }
		grid.ownerGrid.refreshRank();
   },
   onSendBrd: function () 
   {
   },

   	onBeforeRender: function (grid) {
   	},
   	
   	init: function(view) {

		view.plugins[0].addListener('beforeEdit', function(rowEditing, context) {
			return context.record.get('decided');
		});
   	
   	}

    
});
