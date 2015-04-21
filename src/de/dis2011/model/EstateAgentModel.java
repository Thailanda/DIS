package de.dis2011.model;

import de.dis2011.data.EstateAgent;

public class EstateAgentModel extends EntityModel<EstateAgent>{

	final private static String[] COLUMNS = {"ID", "Name", "Address", "Login", "Password"};

    @Override
    public Object getValueAt(int i, int i1) {
        EstateAgent agent = findByRow(i);
        switch (i1) {
            case 0: return agent.getId();
            case 1: return agent.getName();
            case 2: return agent.getAddress();
            case 3: return agent.getLogin();
            case 4: return agent.getPassword().replaceAll("\\w", "*");
        }
        return null;
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        EstateAgent agent = findByRow(i);
        switch (i1) {
            case 1: agent.setName((String) o); break;
            case 2: agent.setAddress((String) o); break;
            case 3: agent.setLogin((String) o); break;
            case 4: agent.setPassword((String) o); break;
        }
        agent.save();
    }

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }
	
}
