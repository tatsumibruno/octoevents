SET @id_issue=nextval('issue_seq');
insert into issue (id, number, state, title, body, created_at) values (@id_issue, 1, 'open', 'Issue number 1', 'Content of issue number 1', '2021-02-27 08:00:00-03');
insert into issue_event (id, issue_id, action, generated_at) values (nextval('issue_event_seq'), @id_issue, 'open', '2021-02-27 08:00:00-03');
insert into issue_event (id, issue_id, action, generated_at) values (nextval('issue_event_seq'), @id_issue, 'edited', '2021-02-27 08:01:00-03');
insert into issue_event (id, issue_id, action, generated_at) values (nextval('issue_event_seq'), @id_issue, 'closed', '2021-02-27 08:10:00-03');
update issue set state = 'closed' where id = @id_issue;