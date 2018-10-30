Ext.define('Luntan.view.courses.CourseModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.coursemodel',

    data: {
		current : {
			edocId : '',
			ci : null
		}
    },
    
    stores: {
		coursegroups: 'CourseGroupStore',    	
		courses: 'CourseStore',    	
  
		extradesstore: {
			type: 'chained',
			source: 'CIDesignationStore'
		},    	
 		usedYears: {
 			type:'chained',
 			source:'EconomyDocStore',
 			sorters: [{property:'year', direction: 'DESC'}]
 		},
		cistore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'economyDocId', value: '{current.edocId}', exactMatch: true}],
			sorters: [{property:'courseName', direction: 'ASC'}],
			groupField: 'courseGroup'
		},    	
		citaskstore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'economyDocId', value: '{current.edocId}', exactMatch: true}],
			sorters: [{property:'courseName', direction: 'ASC'}],
			groupField: 'courseGroup'
		},
		fmstore : {
			type: 'chained',
			source: 'FundingModelStore'
		}
    	
/*
		courses: {
			type: 'chained',
			source: 'CourseStore'
		}
 */
		    	
	},
	
	formulas: {
        workingEDoc: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{comboCurrentYear.selection.id}', //--> reference configurated on the grid view (reference: comboCurrentYear)
                deep: true
            },
            get: function(edocId) {
            	this.set('current.edocId', edocId);
                return edocId;
            }
        },
 
        currentCI: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{ciList.selection}', //--> reference configurated on the grid view (reference: ouList)
                deep: true
            },
            get: function(ci) {
            	this.set('current.ci', ci);
                return ci;
            },
        }
 

	}

});
