Ext.define('Luntan.view.courses.CourseInstanceCloneController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.courseinstanceclone',

    onSelectionChange: function(sm, rec) {},

	onNewFromYear: function() {
		this.getView().getSelectionModel().deselectAll();
	},
	
   onRemove: function()
    {
        var sm = this.getView().getSelectionModel();
            
        sm.getStore().remove(sm.getSelection());
		sm.getStore().getSource().sync();

    },

	onItemsClone: function() {
		var dest = this.getViewModel().get('cloning.cloneDestED'),
			sm = this.getView().getSelectionModel()
			store = this.getView().getStore(), 
			ciStore = Ext.getStore('CourseInstanceStore');
		
		sm.getSelected().each(function(ci) {
			/* Only create a clone if an equivalent course instance does not exist yet */
			if (ciStore.findBy(function(record,id) {
						return (dest.id == record.get('economyDocId')  && ci.get('courseId') == record.get('courseId')  && ci.get('extraDesignation') == record.get('extraDesignation'));
					}) < 0) 
				{
					var nci = ci.copy(null);
					nci.set('preceedingCIId',ci.id);
					nci.set('economyDocId',dest.id);
					nci.set('firstInstance',false);
					nci.set('registeredStudents',null);
					nci.set('startRegStudents',null);
					nci.set('note',"");
					nci.set('balanceRequest',false);


					var rec = store.insert(0, nci);
				} 
		});

		sm.getStore().getSource().sync();
		this.getView().getSelectionModel().deselectAll();
	},


});
