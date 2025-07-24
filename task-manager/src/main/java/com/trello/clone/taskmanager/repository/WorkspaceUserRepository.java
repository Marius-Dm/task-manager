package com.trello.clone.taskmanager.repository;

import com.trello.clone.taskmanager.model.WorkspaceUser;
import com.trello.clone.taskmanager.model.WorkspaceUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, WorkspaceUserId> {
}
