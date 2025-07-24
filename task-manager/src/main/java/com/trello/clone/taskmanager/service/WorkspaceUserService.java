package com.trello.clone.taskmanager.service;

import com.trello.clone.taskmanager.model.WorkspaceUser;
import com.trello.clone.taskmanager.model.WorkspaceUserId;
import com.trello.clone.taskmanager.model.WorkspaceUserRole;
import com.trello.clone.taskmanager.repository.WorkspaceUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkspaceUserService {

    private final WorkspaceUserRepository workspaceUserRepository;

    public WorkspaceUserService(WorkspaceUserRepository workspaceUserRepository) {
        this.workspaceUserRepository = workspaceUserRepository;
    }

    @Transactional
    public WorkspaceUser saveWorkspaceUser(WorkspaceUser workspaceUser) {
        if (workspaceUser.getWorkspace() == null || workspaceUser.getUser() == null) {
            throw new IllegalArgumentException("Workspace and User must not be null");
        }
        return workspaceUserRepository.save(workspaceUser);
    }

    @Transactional
    public void deleteWorkspaceUser(WorkspaceUserId id) {
        if (!workspaceUserRepository.existsById(id)) {
            throw new IllegalArgumentException("WorkspaceUser with id " + id + " does not exist");
        }
        workspaceUserRepository.deleteById(id);
    }

}
