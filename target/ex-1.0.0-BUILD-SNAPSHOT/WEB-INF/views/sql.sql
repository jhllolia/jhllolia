
    CREATE TABLE TB_VISITOR(
        VISIT_ID INT PRIMARY KEY,--기본키 , 시퀀스 달것임
        VISIT_IP VARCHAR(100) NOT NULL, --접속자 아이피
        VISIT_TIME DATETIME NOT NULL, --접속자 접속시간
        VISIT_REFER VARCHAR(300) NOT NULL, --접속자가 어느사이트를 타고 들어왔는지
        VISIT_AGENT VARCHAR(400) NOT NULL --접속자 브라우저 정보
    )
				

SELECT * FROM (
									SELECT	 *
									FROM     bbs_board_tb
									WHERE    board_Category = 'Notice'
									AND		 board_Seq <= 3
									ORDER BY board_Seq
							  ) A
				UNION ALL
		       	
				SELECT * FROM (
									SELECT	 *
									FROM     bbs_board_tb
									WHERE    board_Category = 'Notice'
									ORDER BY board_Seq DESC
							  ) B

							  
INSERT	INTO	member_tb 	(
								member_Id,
								member_Pw,
								member_Name,
								member_Email,
								register_Date
						  	) values (
										'root',
										'1234',
										'전준호',
										'jhllolia@naver.com',
										now()
							);
							

				(
		       		SELECT	 	A.*
					FROM     	bbs_receipe_tb A 
					WHERE    	A.recipe_Category = '후기'
					AND		 	A.intSeq > 0
					AND		 	A.r_state = 'Y'
					ORDER BY 	A.intSeq DESC
		        )
					UNION ALL
		        (
		        	SELECT		B.*
					FROM		bbs_receipe_tb B
					WHERE		B.recipe_Category = '블로그'
					AND			B.intSeq > 0
					AND			B.r_state = 'Y'
					ORDER BY	B.intSeq DESC
				)
				
				LIMIT		1, 10



		SELECT	*
		FROM 	bbs_receipe_tb
		WHERE	1=1
		AND		intSeq > 0
		AND		r_state = 'Y'
		ORDER BY field(recipe_Category,'후기') DESC, rand() DESC
		limit	#{pageStart}, #{perPageNum}
    									
						