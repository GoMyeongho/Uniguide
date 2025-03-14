
import OAuth from './pages/auth/login/OAuth';
import './style.css';
import { FailPage } from './paySystem/Fail';
import { SuccessPage } from './paySystem/Success';
import GlobalStyle from './styles/GlobalStyle';
import Layout from './styles/Layout';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ModalExample from "./example/ModalExample";
import AccordionExample from "./example/AccordionExample";
import AdminNav from "./pages/admin/AdminNav";
import PermissionMain from "./pages/admin/auth/list/PermissionMain";
import PermissionStore from "./context/admin/PermissionStore";
import ChatStore from './context/ChatStore';
import MyPageNavBar from "./component/MyPageNavBar";
import TextStore, { PostLayout } from "./context/TextStore";
import PostListMain from "./pages/text/post/list/PostListMain";
import PostItemMain from "./pages/text/post/item/PostItemMain";
import CoverLetterRegister from './pages/myPage/CoverLetterRegister';
import PersonalStatement from './pages/categoryEnumPS/PersonalStatement';
import PersonalStatementDetail from './pages/categoryEnumPS/PersonalStatementDetail';
import PersonalStatementWrite from './pages/categoryEnumPS/PersonalStatementWrite';
import StudentRecord from './pages/categoryEnumSR/StudentRecord';
import StudentRecordDetail from './pages/categoryEnumSR/StudentRecordDetail';
import PurchasedEnumSR from './pages/myPage/PurchasedEnumSR';
import PurchasedEnumPS from './pages/myPage/PurchasedEnumPS';
import FileUploaderExample from "./example/FileUploaderExample";
import UploadedEnumPS from './pages/myPage/UploadedEnumPS';
import UploadedEnumSR from './pages/myPage/UploadedEnumSR';
import PermissionDetailMain from "./pages/admin/auth/item/PermissionDetailMain";
import MemberEdit from './pages/member/update/MemberEdit';
import Permission from './pages/member/info/Permission';
import Withdrawal from './pages/member/info/Withdrawal';
import { CheckoutPage } from './paySystem/CheckOut';
import Store from "./context/Store"
import {Provider} from "react-redux";
import PostCreateMain from "./pages/text/write/create/PostCreateMain";
import MemberControlMain from "./pages/admin/member/list/MemberControlMain";
import BoardControlMain from "./pages/admin/board/BoardControlMain";
import MemberItemDetail from "./pages/admin/member/item/MemberItemDetail";
import AdminMain from "./pages/admin/AdminMain";
import Main from "./pages/Main";
import Terms from "./styles/Terms";



function App() {
  return (
    <>
      <Provider store={Store}>
        <GlobalStyle />
        {/* Router를 최상위에서만 사용 */}
        <Router>
          <Routes>
            {/* 메인 레이아웃 적용 */}
            <Route path="/" element={<ChatStore><Layout/></ChatStore>}>
              <Route path="" element={<Main/>}/>
              <Route path="personalStatement/:id?" element={<PersonalStatement />} />
              <Route path="personalStatementDetail/:id" element={<PersonalStatementDetail type="ps" />} />
              <Route path="personalStatementWrite/:id?" element={<PersonalStatementWrite />} />
              <Route path='studentRecord/:id?' element={<StudentRecord/>}/>
              <Route path='studentRecordDetail/:id' element={<PersonalStatementDetail type="sr"/>}/>

              {/* 마이페이지 내비게이션 */}
              <Route path="myPageNavBar" element={<MyPageNavBar />}>
                <Route path="coverLetterRegister" element={<CoverLetterRegister />} />
                <Route path="purchasedEnumPS" element={<PurchasedEnumPS />} />
                <Route path="purchasedEnumSR" element={<PurchasedEnumSR />} />
                <Route path="uploadedEnumPS" element={<UploadedEnumPS/>} />
                <Route path="uploadedEnumSR" element={<UploadedEnumSR/>} />
                <Route path="memberEdit" element={<MemberEdit />} />
                <Route path="permission" element={<Permission />} />
                <Route path="withdrawal" element={<Withdrawal/>}/>
                <Route path="list/:category/:search/:option" element={<PostListMain active="ACTIVE" />} />
              </Route>

              {/* 테스트 페이지 */}
              <Route path="test/modal" element={<ModalExample />} />
              <Route path="test/accordion" element={<AccordionExample />} />
              <Route path="test/upload" element={<FileUploaderExample/>}/>

              {/* 어드민 페이지 */}
              <Route path="admin" element={<PermissionStore><AdminNav /></PermissionStore>}>
                <Route path="main" element={<AdminMain/>}/>
                <Route path="auth" element={<PermissionMain />} />
                <Route path="auth/:permissionId" element={<PermissionDetailMain />} />
                <Route path="member/:searchOption/:searchValue" element={<MemberControlMain/>} />
                <Route path="member/detail/:id" element={<MemberItemDetail/>}/>
                <Route path="board/:category/:search?/:option?" element={<BoardControlMain/>}/>
              </Route>

              {/* 게시판 (text Board) */}
              <Route path="post" element={<TextStore><PostLayout /></TextStore>}>
                <Route path="list/:category/:search?/:option?" element={<PostListMain active="ACTIVE" />} />
                <Route path="detail/:id" element={<PostItemMain />} />
                <Route path="create/:category/:id?" element={<PostCreateMain />} />
              </Route>
              <Route path='auth/oauth-response/:token/:expirationTime' element={<OAuth/>}/>
              <Route path='privacy-policy' element={<Terms/>}/>
            </Route>
            {/* 결제 관련 페이지 */}
            <Route path="checkoutPage" element={<CheckoutPage />} />
            <Route path="sandbox/success" element={<SuccessPage />} />
            <Route path="checkoutPage/fail" element={<FailPage />} />
          </Routes>
        </Router>
      </Provider>
    </>
  );
}

export default App;
