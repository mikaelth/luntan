Ext.define('Luntan.view.courses.ExaminerModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.examinermodel',

    data: {
		current : {
			cid : null
		}
    },
    
    stores: {
		coursegroups: 'CourseGroupStore',    	
		courses: 'CourseStore',    	
  
		teachers : {
			type: 'chained',
			source: 'TeacherStore'
		},
		examiners : {
			type: 'chained',
			source: 'ExaminerStore',
			filters: [
				{property: 'courseId', value: '{current.cid}', exactMatch: true}, 
				{property: 'decided', value: false, exactMatch: true}
			],
//			sorters: {property:'rank', direction: 'ASC'},
			nextRank : function() { 
				var rs = this.data.items, v = 0; 
				for(var i = 0; i < rs.length; i++){ 
					v = Math.max(v, (1 * rs[i].data['rank']) || 0); 
				} 
				return v+1; 
			} 			
			
		}
		    	
	},
	
	formulas: {

        currentCourse: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{courselist.selection.id}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(cid) {
            	this.set('current.cid', cid);
                return cid;
            }
 
		}
 	}

});
